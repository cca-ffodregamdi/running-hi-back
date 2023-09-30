package com.runninghi.adminpost.command.application.controller;

import com.runninghi.adminpost.command.application.dto.request.AdminPostCreateRequest;
import com.runninghi.adminpost.command.application.dto.request.KeywordListRequest;
import com.runninghi.user.command.domain.aggregate.entity.User;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.user.command.domain.repository.UserCommandRepository;
import jakarta.validation.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import java.util.Arrays;
import java.util.List;
import java.util.Set;


@SpringBootTest
@Transactional
class AdminPostCommandControllerTest {

    @Autowired
    private AdminPostCommandController adminPostCommandController;
    @Autowired
    private UserCommandRepository userCommandRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private ValidatorFactory factory;
    @Autowired
    private Validator validator;

//    @BeforeAll
//    void init() {
//        factory = Validation.buildDefaultValidatorFactory();
//        validator = factory.getValidator();
//    }

    @BeforeEach
    @AfterEach
    void clear() {
        userCommandRepository.deleteAllInBatch();
    }

    private User createAdmin() {
        return userCommandRepository.save(User.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("김철수")
                .nickname("qwe")
                .email("qwe@qwe.qw")
                .role(Role.ADMIN)
                .status(true)
                .build());
    }

    private AdminPostCreateRequest createAdminPostRequest(User admin, List<KeywordListRequest> keywordList) {
        return new AdminPostCreateRequest(
                admin.getId(),
                "asdfiasdnfo.jpg",
                "테스트 제목",
                "테스트 내용",
                keywordList
        );

    }

    private List<KeywordListRequest> createKeywordList() {
        return Arrays.asList(
                KeywordListRequest.of(1L, "낮"),
                KeywordListRequest.of(2L, "밤")
        );
    }

    @DisplayName("관리자 게시글 생성 테스트 : 사용자 키가 null 값일 시 메세지 반환")
    @Test
    void testCreateAdminPostNotLogin() {

        // given
        List<KeywordListRequest> keywordList = createKeywordList();
        AdminPostCreateRequest request = new AdminPostCreateRequest(
                null,
                "asdfiasdnfo.jpg",
                "테스트 제목",
                "테스트 내용",
                keywordList
        );

        BindingResult bindingResult = new BeanPropertyBindingResult(request, "request");

        // when & then
        Assertions.assertThatThrownBy(
                        () -> adminPostCommandController.createAdminPost(request, bindingResult)
                ).isInstanceOf(NullPointerException.class)
                .hasMessage("로그인 후 이용해주세요.");
    }

    @DisplayName("관리자 게시글 생성 요청 유효성 테스트 : 썸네일 null 값일 시 메세지 반환하는 지 확인")
    @Test
    void testCreateAdminPostNullThumbnail () {

        // given
        User admin = createAdmin();
        List<KeywordListRequest> keywordList = createKeywordList();
        AdminPostCreateRequest request = new AdminPostCreateRequest(
                admin.getId(),
                null,
                "테스트 제목",
                "테스트 내용",
                keywordList
        );

        // when
        Set<ConstraintViolation<AdminPostCreateRequest>> violations =
                validator.validate(request);

        // then
        Assertions.assertThat(violations.iterator().next().getMessage())
                .isEqualTo("썸네일 사진을 등록해주세요.");
    }

    @DisplayName("관리자 게시글 생성 요청 유효성 테스트 : 썸네일 \"\"일 시 메세지 반환하는 지 학인")
    @Test
    void testCreateAdminPostNoThumbnail () {

        // given
        User admin = createAdmin();
        List<KeywordListRequest> keywordList = createKeywordList();
        AdminPostCreateRequest request = new AdminPostCreateRequest(
                admin.getId(),
                "",
                "테스트 제목",
                "테스트 내용",
                keywordList
        );

        // when
        Set<ConstraintViolation<AdminPostCreateRequest>> violations =
                validator.validate(request);

        // then
        Assertions.assertThat(violations.iterator().next().getMessage())
                .isEqualTo("썸네일 사진을 등록해주세요.");
    }

    @DisplayName("관리자 게시글 생성 요청 유효성 테스트 : 썸네일 \" \"일 시 메세지 반환하는 지 학인")
    @Test
    void testCreateAdminPostSapceBarThumbnail () {

        // given
        User admin = createAdmin();
        List<KeywordListRequest> keywordList = createKeywordList();
        AdminPostCreateRequest request = new AdminPostCreateRequest(
                admin.getId(),
                "  ",
                "테스트 제목",
                "테스트 내용",
                keywordList
        );

        // when
        Set<ConstraintViolation<AdminPostCreateRequest>> violations =
                validator.validate(request);

        // then
        Assertions.assertThat(violations.iterator().next().getMessage())
                .isEqualTo("썸네일 사진을 등록해주세요.");
    }


    @DisplayName("관리자 게시글 생성 요청 유효성 테스트 : 제목 값 null 일 시 예외 메세지 반환하는지 확인")
    @Test
    void testAdminPostRequestTitleValid () {

        // given
        User admin = createAdmin();
        List<KeywordListRequest> keywordList = createKeywordList();
        AdminPostCreateRequest request = new AdminPostCreateRequest(
                admin.getId(),
                "asdfiasdnfo.jpg",
                null,
                "테스트 내용",
                keywordList
        );

        // when
        Set<ConstraintViolation<AdminPostCreateRequest>> violations =
                validator.validate(request);

        // then
        Assertions.assertThat(violations.iterator().next().getMessage())
                .isEqualTo("제목을 입력해주세요.");
    }

    @DisplayName("관리자 게시글 생성 요청 유효성 테스트 : 제목 값 \"\"일 시 예외 메세지 반환하는지 확인")
    @Test
    void testAdminPostRequestNoTitleValid () {

        // given
        User admin = createAdmin();
        List<KeywordListRequest> keywordList = createKeywordList();
        AdminPostCreateRequest request = new AdminPostCreateRequest(
                admin.getId(),
                "asdfiasdnfo.jpg",
                "",
                "테스트 내용",
                keywordList
        );

        // when
        Set<ConstraintViolation<AdminPostCreateRequest>> violations =
                validator.validate(request);

        // then
        Assertions.assertThat(violations.iterator().next().getMessage())
                .isEqualTo("제목을 입력해주세요.");
    }

    @DisplayName("관리자 게시글 생성 요청 유효성 테스트 : 제목 값 \" \" 일 시 예외 메세지 반환하는지 확인")
    @Test
    void testAdminPostRequestSapceTitleValid () {

        // given
        User admin = createAdmin();
        List<KeywordListRequest> keywordList = createKeywordList();
        AdminPostCreateRequest request = new AdminPostCreateRequest(
                admin.getId(),
                "asdfiasdnfo.jpg",
                " ",
                "테스트 내용",
                keywordList
        );

        // when
        Set<ConstraintViolation<AdminPostCreateRequest>> violations =
                validator.validate(request);

        // then
        Assertions.assertThat(violations.iterator().next().getMessage())
                .isEqualTo("제목을 입력해주세요.");
    }

    @DisplayName("관리자 게시글 생성 요청 유효성 테스트 : 내용 null 일 시 예외 메세지 반환하는지 확인")
    @Test
    void testAdminPostRequestContentNull () {

        // given
        User admin = createAdmin();
        List<KeywordListRequest> keywordList = createKeywordList();
        AdminPostCreateRequest request = new AdminPostCreateRequest(
                admin.getId(),
                "asdfiasdnfo.jpg",
                "테스트 제목",
                null,
                keywordList
        );

        // when
        Set<ConstraintViolation<AdminPostCreateRequest>> violations = validator.validate(request);

        // then
        Assertions.assertThat(violations.iterator().next().getMessage())
                .isEqualTo("게시글 내용을 입력해주세요.");

    }

    @DisplayName("관리자 게시글 생성 요청 유효성 테스트 : 내용 \"\"일 시 예외 메세지 반환하는지 확인")
    @Test
    void testAdminPostRequestNoContent () {

        // given
        User admin = createAdmin();
        List<KeywordListRequest> keywordList = createKeywordList();
        AdminPostCreateRequest request = new AdminPostCreateRequest(
                admin.getId(),
                "asdfiasdnfo.jpg",
                "테스트 제목",
                "",
                keywordList
        );

        // when
        Set<ConstraintViolation<AdminPostCreateRequest>> violations = validator.validate(request);

        // then
        Assertions.assertThat(violations.iterator().next().getMessage())
                .isEqualTo("게시글 내용을 입력해주세요.");

    }

    @DisplayName("관리자 게시글 생성 요청 유효성 테스트 : 내용 \" \" 시 예외 메세지 반환하는지 확인")
    @Test
    void testAdminPostRequestSpaceContent () {

        // given
        User admin = createAdmin();
        List<KeywordListRequest> keywordList = createKeywordList();
        AdminPostCreateRequest request = new AdminPostCreateRequest(
                admin.getId(),
                "asdfiasdnfo.jpg",
                "테스트 제목",
                " ",
                keywordList
        );

        // when
        Set<ConstraintViolation<AdminPostCreateRequest>> violations = validator.validate(request);

        // then
        Assertions.assertThat(violations.iterator().next().getMessage())
                .isEqualTo("게시글 내용을 입력해주세요.");

    }

}