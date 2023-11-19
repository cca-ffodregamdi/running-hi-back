package com.runninghi.Image.command.application.service;

import com.runninghi.image.command.application.dto.request.ImageDeletePostRequest;
import com.runninghi.image.command.application.dto.request.ImageRequest;
import com.runninghi.image.command.application.dto.response.ImageResponse;
import com.runninghi.image.command.application.service.ImageCommandService;
import com.runninghi.image.command.domain.aggregate.entity.Image;
import com.runninghi.image.command.domain.repository.ImageCommandRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class ImageCommandServiceTests {

    @Autowired
    private ImageCommandService imageCommandService;

    @Autowired
    private ImageCommandRepository imageCommandRepository;

    @AfterEach
    void clear() {
        imageCommandRepository.deleteAllInBatch();
    }


    // 관리자 게시글과 관련된 이미지 entity 생성
    @Test
    @DisplayName("관리자 게시글과 관련된 이미지 entity 1개 생성 : success")
    public void createImageEntityWithAdminPostNo() {

        long beforeCnt = imageCommandRepository.count();

        // 이미지 entity 생성
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("testUrls");
        ImageRequest request = new ImageRequest(imageUrls, 1L);

        // 생성한 image entity 조회
        ImageResponse response = imageCommandService.createAdminPostImage(request);
        List<Image> images = imageCommandRepository.findAllById(response.imageNos());

        long afterCnt = imageCommandRepository.count();

        Assertions.assertEquals(beforeCnt + 1, afterCnt);
        Assertions.assertEquals(1L, images.get(0).getAdminPostVO().getAdminPostNo());

    }

    @Test
    @DisplayName("관리자 게시글과 관련된 이미지 entity 여러 개 생성 : success")
    public void createImagesEntityWithAdminPostNo() {

        long beforeCnt = imageCommandRepository.count();

        // 이미지 entity 생성
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("testUrls1");
        imageUrls.add("testUrls2");
        ImageRequest request = new ImageRequest(imageUrls, 1L);

        // 생성한 image entity 조회
        ImageResponse response = imageCommandService.createAdminPostImage(request);
        List<Image> images = imageCommandRepository.findAllById(response.imageNos());

        long afterCnt = imageCommandRepository.count();

        Assertions.assertEquals(beforeCnt + 2, afterCnt);
        Assertions.assertEquals(1L, images.get(0).getAdminPostVO().getAdminPostNo());
        Assertions.assertEquals(1L, images.get(1).getAdminPostVO().getAdminPostNo());

    }

    // 관리자 게시글과 관련된 이미지 entity 삭제
    @Test
    @DisplayName("관리자 게시글과 관련된 이미지 entity 여러 개 삭제 : success")
    public void deleteImagesEntityWithAdminPostNo() {

        // 이미지 entity 생성
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("testUrls1");
        imageUrls.add("testUrls2");
        ImageRequest imageRequest = new ImageRequest(imageUrls, 1L);

        // 생성한 image entity 조회
        ImageResponse imageResponse = imageCommandService.createAdminPostImage(imageRequest);

        long beforeCnt = imageCommandRepository.count();

        ImageDeletePostRequest request = new ImageDeletePostRequest(1L);
        List<String> deletedImageUrls = imageCommandService.deleteAdminPostImage(request);

        long afterCnt = imageCommandRepository.count();

        List<Image> response = imageCommandRepository.findAllById(imageResponse.imageNos());

        Assertions.assertEquals(beforeCnt - 2, afterCnt);
        Assertions.assertEquals(0, response.size());
        Assertions.assertEquals("testUrls1", deletedImageUrls.get(0));
        Assertions.assertEquals("testUrls2", deletedImageUrls.get(1));

    }

    // 유저 게식글과 관련된 이미지 entity 생성
    @Test
    @DisplayName("유저 게시글과 관련된 이미지 entity 1개 생성 : success")
    public void createImageEntityWithUserPostNo() {

        long beforeCnt = imageCommandRepository.count();

        // 이미지 entity 생성
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("testUrls");
        ImageRequest request = new ImageRequest(imageUrls, 1L);

        // 생성한 image entity 조회
        ImageResponse response = imageCommandService.createUserPostImage(request);
        List<Image> images = imageCommandRepository.findAllById(response.imageNos());

        long afterCnt = imageCommandRepository.count();

        Assertions.assertEquals(beforeCnt + 1, afterCnt);
        Assertions.assertEquals(1L, images.get(0).getUserPostVO().getUserPostNo());

    }

    @Test
    @DisplayName("유저 게시글과 관련된 이미지 entity 여러 개 생성 : success")
    public void createImagesEntityWithUserPostNo() {

        long beforeCnt = imageCommandRepository.count();

        // 이미지 entity 생성
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("testUrls1");
        imageUrls.add("testUrls2");
        ImageRequest request = new ImageRequest(imageUrls, 1L);

        // 생성한 image entity 조회
        ImageResponse response = imageCommandService.createUserPostImage(request);
        List<Image> images = imageCommandRepository.findAllById(response.imageNos());

        long afterCnt = imageCommandRepository.count();

        Assertions.assertEquals(beforeCnt + 2, afterCnt);
        Assertions.assertEquals(1L, images.get(0).getUserPostVO().getUserPostNo());
        Assertions.assertEquals(1L, images.get(1).getUserPostVO().getUserPostNo());

    }

    // 유저 게시글과 관련된 이미지 entity 삭제
    @Test
    @DisplayName("유저 게시글과 관련된 이미지 entity 여러 개 삭제 : success")
    public void deleteImagesEntityWithUserPostNo() {

        // 이미지 entity 생성
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("testUrls1");
        imageUrls.add("testUrls2");
        ImageRequest imageRequest = new ImageRequest(imageUrls, 1L);

        // 생성한 image entity 조회
        ImageResponse imageResponse = imageCommandService.createUserPostImage(imageRequest);

        long beforeCnt = imageCommandRepository.count();

        ImageDeletePostRequest request = new ImageDeletePostRequest(1L);
        List<String> deletedImageUrls = imageCommandService.deleteUserPostImage(request);

        long afterCnt = imageCommandRepository.count();

        List<Image> response = imageCommandRepository.findAllById(imageResponse.imageNos());

        Assertions.assertEquals(beforeCnt - 2, afterCnt);
        Assertions.assertEquals(0, response.size());
        Assertions.assertEquals("testUrls1", deletedImageUrls.get(0));
        Assertions.assertEquals("testUrls2", deletedImageUrls.get(1));

    }
}
