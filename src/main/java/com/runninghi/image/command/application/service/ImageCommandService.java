package com.runninghi.image.command.application.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.runninghi.image.command.application.dto.request.ImageDeleteRequest;
import com.runninghi.image.command.application.dto.response.ImageCreateResponse;
import com.runninghi.image.command.domain.service.ImageCommandDomainService;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageCommandService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    private final ImageCommandDomainService imageCommandDomainService;


    // 이미지 s3에 업로드
    public ImageCreateResponse uploadImageFile(MultipartFile multipartFile) {

        // 이미지 파일 이름 새로 만들기
        String fileName = createFileName(multipartFile.getOriginalFilename());
        // s3 bucket 의 폴더 중 user 폴더로 지정
        String bucketFolder = "user_post/";

        // s3에 이미지 저장
        try (InputStream inputStream = multipartFile.getInputStream()) {
            // 이미지 압축
            InputStream compressedInputStream = compressImage(inputStream);

            // 이미지 메타데이터 설정
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(compressedInputStream.available());
            objectMetadata.setContentType(multipartFile.getContentType());

            // amazon s3에 저장
            amazonS3.putObject( new PutObjectRequest(bucket, bucketFolder + fileName, compressedInputStream, objectMetadata));

            // 저장된 이미지의 url 반환
            return ImageCreateResponse.from(amazonS3.getUrl(bucket, bucketFolder + fileName).toString());

        } catch (IOException | SdkClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    // 이미지 삭제
    public void deleteImageFile(ImageDeleteRequest imageDeleteRequest) {

        // s3 bucket 의 폴더 중 user 폴더로 지정
        String bucketFolder = "user_post/";

        amazonS3.deleteObject(new DeleteObjectRequest(bucket, imageDeleteRequest.imageUrl().split("/")[3]));

    }

    // 이미지 파일 이름 생성
    private String createFileName(String fileName) {

        LocalDate currentDate = LocalDate.now();

        // 날짜 형식 -> yyyyMMdd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // UUID + 오늘 날짜로 고유한 파일 이름 생성해서 반환
        return UUID.randomUUID().toString().concat(currentDate.format(formatter)).concat(imageCommandDomainService.checkAndGetFileExtension(fileName));

    }

    // 이미지 압축 (이미지 품질을 낮춰서 용량 압축, 원본에 비해 품질이 너무 낮아질 경우 예외처리)
    private InputStream compressImage(InputStream inputStream) {

        try {
            BufferedImage originalImage = ImageIO.read(inputStream);
            int imageWidth = originalImage.getWidth();
            int imageHeight = originalImage.getHeight();
            double outputQuality = 1.0;

            // 압축된 이미지 생성 및 리사이징 반복
            while (true) {

                BufferedImage compressedImage = Thumbnails.of(originalImage)
                        .size(imageWidth, imageHeight)
                        .outputQuality(outputQuality)
                        .asBufferedImage();

                // 포맷 형식을 webp 로 설정 -> 용량을 낮추기 위해
                ByteArrayOutputStream compressedOutputStream = new ByteArrayOutputStream();
                ImageIO.write(compressedImage, "webp", compressedOutputStream);

                byte[] compressedData = compressedOutputStream.toByteArray();
                int compressedSizeKB = compressedData.length / 1024;  // 바이트를 킬로바이트로 변환

                // 압축된 이미지의 크기가 2MB 이하인 경우 반환
                if (compressedSizeKB <= 2000) {
                    return new ByteArrayInputStream(compressedData);
                }

                // 이미지의 크기가 2MB를 넘어갈 경우 퀄리티 조절, 리사이징
                outputQuality -= 0.1;
                imageWidth /= 5;
                imageHeight /= 5;

                if (outputQuality <= 0.5) {
                    // 품질이 너무 낮아져도 2MB 이하 크기를 달성하지 못하는 경우, 예외처리
                    throw new IllegalArgumentException("이미지 용량이 너무 큽니다.");
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
