package com.runninghi.image.command.application.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import com.runninghi.image.command.application.dto.request.ImageDeletePostRequest;
import com.runninghi.image.command.application.dto.request.ImageDeleteRequest;
import com.runninghi.image.command.application.dto.request.ImageRequest;
import com.runninghi.image.command.application.dto.response.ImageCreateResponse;
import com.runninghi.image.command.application.dto.response.ImageResponse;
import com.runninghi.image.command.domain.aggregate.entity.Image;
import com.runninghi.image.command.domain.aggregate.vo.AdminPostVO;
import com.runninghi.image.command.domain.aggregate.vo.UserPostVO;
import com.runninghi.image.command.domain.repository.ImageCommandRepository;
import com.runninghi.image.command.domain.service.ImageCommandDomainService;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageCommandService {

//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//
//    private final AmazonS3 amazonS3;

    @Value("${firebase.storage.bucket}")
    private String bucketName;

    @Value("${firebase.storage.bucket-url}")
    private String bucketUrl;

    private final ImageCommandDomainService imageCommandDomainService;

    private final ImageCommandRepository imageCommandRepository;

//    // 여러 개의 이미지 s3에 저장
//    public List<ImageCreateResponse> uploadImageFiles(List<MultipartFile> multipartFileList) {
//
//        // s3 bucket 의 폴더 중 user 폴더로 지정
//        String bucketfolder = "user_post/";
//        // s3 bucket에 저장 후 반환된 url을 모아둔 list
//        List<ImageCreateResponse> imageUrls = new ArrayList<>();
//
//        for (MultipartFile multipartFile : multipartFileList) {
//            // 이미지 이름 새로 생성
//            String filename = createFileName(multipartFile.getOriginalFilename());
//
//            try (InputStream inputStream = multipartFile.getInputStream()) {
//                // 이미지 압축
//                InputStream compressedInputStream = compressImage(inputStream);
//
//                ObjectMetadata objectMetadata = new ObjectMetadata();
//                objectMetadata.setContentLength(compressedInputStream.available());
//                objectMetadata.setContentType(multipartFile.getContentType());
//
//                // s3에 저장
//                amazonS3.putObject(new PutObjectRequest(bucket, bucketfolder + filename, compressedInputStream, objectMetadata));
//
//                // url list 저장된 이미지의 url 저장
//                String imageUrl = amazonS3.getUrl(bucket, bucketfolder + filename).toString();
//                imageUrls.add(ImageCreateResponse.from(imageUrl));
//
//            } catch (IOException | SdkClientException e) {
//                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
//            }
//        }
//
//        return imageUrls;
//
//    }
//
//    // 이미지 s3에 업로드
//    public ImageCreateResponse uploadImageFile(MultipartFile multipartFile, String board) {
//
//        // 이미지 파일 이름 새로 만들기
//        String fileName = createFileName(multipartFile.getOriginalFilename());
//        // s3 bucket 의 폴더 지정
//        String bucketFolder = (board.equals("admin")) ? "admin_post/" : "user_post/";
//
//        // s3에 이미지 저장
//        try (InputStream inputStream = multipartFile.getInputStream()) {
//            // 이미지 압축
//            InputStream compressedInputStream = compressImage(inputStream);
//
//            // 이미지 메타데이터 설정
//            ObjectMetadata objectMetadata = new ObjectMetadata();
//            objectMetadata.setContentLength(compressedInputStream.available());
//            objectMetadata.setContentType(multipartFile.getContentType());
//
//            // amazon s3에 저장
//            amazonS3.putObject( new PutObjectRequest(bucket, bucketFolder + fileName, compressedInputStream, objectMetadata));
//
//            // 저장된 이미지의 url 반환
//            return ImageCreateResponse.from(amazonS3.getUrl(bucket, bucketFolder + fileName).toString());
//
//        } catch (IOException | SdkClientException e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
//        }
//
//    }
//
//    // s3에서 하나의 이미지 삭제
//    public void deleteImageFile(ImageDeleteRequest imageDeleteRequest) {
//
//        // s3 bucket 의 폴더 지정
//        String bucketFolder = (imageDeleteRequest.board().equals("admin")) ? "admin_post/" : "user_post/";
//
//        amazonS3.deleteObject(new DeleteObjectRequest(bucket, imageDeleteRequest.imageUrl().split("/")[3]));
//
//    }
//
//    // 유저 게시판 : 특정 게시물과 관련된 이미지 entity 생성
//    public ImageResponse createUserPostImage(ImageRequest imageRequest) {
//
//        UserPostVO userPostVO = new UserPostVO(imageRequest.postNo());
//
//        // 이미지 entity 저장
//        List<Image> imageEntities = imageRequest.imageUrls().stream()
//                .map(imageUrl -> {
//                    Image image = new Image.Builder()
//                            .imageUrl(imageUrl)
//                            .userPostVO(userPostVO)
//                            .build();
//                    return imageCommandRepository.save(image);
//                })
//                .toList();
//
//        // 생성한 이미지 entity 들의 imageNo만 모아서 반환
//        List<Long> imageNos = imageEntities.stream()
//                .map(Image::getImageNo)
//                .toList();
//
//        return new ImageResponse(imageNos);
//    }
//
//    // 유저 게시판 : 특정 게시물과 관련된 s3이미지, 이미지 entity 전부 삭제
//    public void deleteUserPostImage(ImageDeletePostRequest imageDeletePostRequest) {
//
//        UserPostVO userPostVO = new UserPostVO(imageDeletePostRequest.postNo());
//
//        // 이미지 entity 전부 삭제
//        imageCommandRepository.deleteImagesByUserPostVO(userPostVO);
//
//    }
//
//    // 관리자 게시판 : 특정 게시물과 관련된 이미지 entity 생성
//    public ImageResponse createAdminPostImage(ImageRequest imageRequest) {
//
//        AdminPostVO adminPostVO = new AdminPostVO(imageRequest.postNo());
//
//        // 이미지 entity 저장
//        List<Image> imageEntities = imageRequest.imageUrls().stream()
//                .map(imageUrl -> {
//                    Image image = new Image.Builder()
//                            .imageUrl(imageUrl)
//                            .adminPostVO(adminPostVO)
//                            .build();
//                    return imageCommandRepository.save(image);
//                })
//                .toList();
//
//        List<Long> imageNos = imageEntities.stream()
//                .map(Image::getImageNo)
//                .toList();
//
//        return new ImageResponse(imageNos);
//
//    }
//
//    // 관리자 게시판 : 특정 게시물과 관련된 s3이미지, 이미지 entity 전부 삭제
//    public void deleteAdminPostImage(ImageDeletePostRequest imageDeletePostRequest) {
//
//        AdminPostVO adminPostVO = new AdminPostVO(imageDeletePostRequest.postNo());
//
//        // 이미지 entity 전부 삭제
//        imageCommandRepository.deleteImagesByAdminPostVO(adminPostVO);
//
//    }
//
//    // 이미지 파일 이름 생성
//    private String createFileName(String fileName) {
//
//        LocalDate currentDate = LocalDate.now();
//
//        // 날짜 형식 -> yyyyMMdd
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//
//        // UUID + 오늘 날짜로 고유한 파일 이름 생성해서 반환
//        return UUID.randomUUID().toString().concat(currentDate.format(formatter)).concat(imageCommandDomainService.checkAndGetFileExtension(fileName));
//
//    }
//
//    // 이미지 압축 (이미지 품질을 낮춰서 용량 압축, 원본에 비해 품질이 너무 낮아질 경우 예외처리)
//    private InputStream compressImage(InputStream inputStream) {
//
//        try {
//            BufferedImage originalImage = ImageIO.read(inputStream);
//            int imageWidth = originalImage.getWidth();
//            int imageHeight = originalImage.getHeight();
//            double outputQuality = 1.0;
//
//            // 압축된 이미지 생성 및 리사이징 반복
//            while (true) {
//
//                BufferedImage compressedImage = Thumbnails.of(originalImage)
//                        .size(imageWidth, imageHeight)
//                        .outputQuality(outputQuality)
//                        .asBufferedImage();
//
//                // 포맷 형식을 webp 로 설정 -> 용량을 낮추기 위해
//                ByteArrayOutputStream compressedOutputStream = new ByteArrayOutputStream();
//                ImageIO.write(compressedImage, "webp", compressedOutputStream);
//
//                byte[] compressedData = compressedOutputStream.toByteArray();
//                int compressedSizeKB = compressedData.length / 1024;  // 바이트를 킬로바이트로 변환
//
//                // 압축된 이미지의 크기가 2MB 이하인 경우 반환
//                if (compressedSizeKB <= 2000) {
//                    return new ByteArrayInputStream(compressedData);
//                }
//
//                // 이미지의 크기가 2MB를 넘어갈 경우 퀄리티 조절, 리사이징
//                outputQuality -= 0.1;
//                imageWidth /= 5;
//                imageHeight /= 5;
//
//                if (outputQuality <= 0.5) {
//                    // 품질이 너무 낮아져도 2MB 이하 크기를 달성하지 못하는 경우, 예외처리
//                    throw new IllegalArgumentException("이미지 용량이 너무 큽니다.");
//                }
//
//            }
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

    // 여러 개의 이미지 Firebase Storage에 저장
    @Transactional
    public List<ImageCreateResponse> uploadImageFiles(List<MultipartFile> multipartFileList, String board) {
        // Firebase Storage 버킷 지정
        Bucket bucket = StorageClient.getInstance().bucket(bucketName);

        // Firebase Storage에 저장 후 반환된 URL을 모아둔 list
        List<ImageCreateResponse> imageUrls = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFileList) {
            // 이미지 이름 새로 생성
            String fileName = createFileName(multipartFile.getOriginalFilename());
            String folder = (board.equals("admin")) ? "admin_post/" : "user_post/";

            try (InputStream inputStream = multipartFile.getInputStream()) {
                // 이미지 압축
                InputStream compressedInputStream = compressImage(inputStream);

                // Firebase Storage에 이미지 저장
                BlobId blobId = BlobId.of(bucketName, folder + fileName);
                BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(multipartFile.getContentType()).build();
                Blob blob = bucket.create(folder + fileName, compressedInputStream, blobInfo.getContentType());

                // 이미지에 접근하는 조회용 url 생성
                folder = folder.replace("/", "%2F");
                String imageUrl = bucketUrl + folder + fileName + "?alt=media";

                imageUrls.add(ImageCreateResponse.from(imageUrl));

            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }

        return imageUrls;
    }

    // 이미지 Firebase Storage에 업로드
    @Transactional
    public ImageCreateResponse uploadImageFile(MultipartFile multipartFile, String board) {
        // Firebase Storage 버킷 지정
        Bucket bucket = StorageClient.getInstance().bucket(bucketName);

        // 이미지 파일 이름 새로 만들기
        String fileName = createFileName(multipartFile.getOriginalFilename());
        String folder = (board.equals("admin")) ? "admin_post/" : "user_post/";

        try (InputStream inputStream = multipartFile.getInputStream()) {
            // 이미지 압축
            InputStream compressedInputStream = compressImage(inputStream);

            // Firebase Storage에 이미지 저장
            BlobId blobId = BlobId.of(bucketName, folder + fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(multipartFile.getContentType()).build();
            Blob blob = bucket.create(folder + fileName, compressedInputStream, blobInfo.getContentType());

            // 이미지에 접근하는 조회용 url 생성
            folder = folder.replace("/", "%2F");
            String imageUrl = bucketUrl + folder + fileName + "?alt=media";

            return ImageCreateResponse.from(imageUrl);

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // Firebase Storage에서 이미지 삭제
    @Transactional
    public void deleteImageFile(ImageDeleteRequest imageDeleteRequest) {
        String folder = (imageDeleteRequest.board().equals("admin")) ? "admin_post/" : "user_post/";
        String imageName = imageDeleteRequest.imageUrl().replace(bucketUrl, "").replace("%2F", "/").replace("?alt=media", "");
        BlobId blobId = BlobId.of(bucketName,  imageName);
        Storage storage = StorageClient.getInstance().bucket(bucketName).getStorage();
        boolean deleted = storage.delete(blobId);

        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete the image");
        }
    }

    // 유저 게시판: 특정 게시물과 관련된 Firebase Storage 이미지 및 이미지 entity 전부 삭제
    @Transactional
    public void deleteUserPostImage(ImageDeletePostRequest imageDeletePostRequest) {
        UserPostVO userPostVO = new UserPostVO(imageDeletePostRequest.postNo());

        List<String> imageUrls = imageCommandRepository.getImagesByUserPostVO(userPostVO);

        // 이미지 entity 전부 삭제
        imageCommandRepository.deleteImagesByUserPostVO(userPostVO);

        // Firebase Storage에서 이미지 삭제 (옵션)
        imageUrls.forEach(imageUrl -> {
            String imageName = imageUrl.replace(bucketUrl, "").replace("%2F", "/").replace("?alt=media", "");
            BlobId blobId = BlobId.of(bucketName, imageName);
            Storage storage = StorageClient.getInstance().bucket(bucketName).getStorage();
            boolean deleted = storage.delete(blobId);

            if (!deleted) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete the image");
            }
        });
    }

    // 관리자 게시판: 특정 게시물과 관련된 이미지 entity 생성
    public ImageResponse createAdminPostImage(ImageRequest imageRequest) {
        AdminPostVO adminPostVO = new AdminPostVO(imageRequest.postNo());

        // 이미지 entity 저장
        List<Image> imageEntities = imageRequest.imageUrls().stream()
                .map(imageUrl -> {
                    Image image = new Image.Builder()
                            .imageUrl(imageUrl)
                            .adminPostVO(adminPostVO)
                            .build();
                    return imageCommandRepository.save(image);
                })
                .toList();

        // 생성한 이미지 entity들의 imageNo만 모아서 반환
        List<Long> imageNos = imageEntities.stream()
                .map(Image::getImageNo)
                .toList();

        return new ImageResponse(imageNos);
    }

    // 관리자 게시판: 특정 게시물과 관련된 Firebase Storage 이미지 및 이미지 entity 전부 삭제
    public void deleteAdminPostImage(ImageDeletePostRequest imageDeletePostRequest) {
        AdminPostVO adminPostVO = new AdminPostVO(imageDeletePostRequest.postNo());

        List<String> imageUrls = imageCommandRepository.getImagesByAdminPostVO(adminPostVO);

        // 이미지 entity 전부 삭제
        imageCommandRepository.deleteImagesByAdminPostVO(adminPostVO);

        // Firebase Storage에서 이미지 삭제 (옵션)
        imageUrls.forEach(imageUrl -> {
            String imageName = imageUrl.replace(bucketUrl, "").replace("%2F", "/").replace("?alt=media", "");
            BlobId blobId = BlobId.of(bucketName, imageName);
            Storage storage = StorageClient.getInstance().bucket(bucketName).getStorage();
            boolean deleted = storage.delete(blobId);

            if (!deleted) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete the image");
            }
        });
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

                // 포맷 형식을 webp로 설정 -> 용량을 낮추기 위해
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
