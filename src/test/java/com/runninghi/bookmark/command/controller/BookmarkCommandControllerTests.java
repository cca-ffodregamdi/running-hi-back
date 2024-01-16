package com.runninghi.bookmark.command.controller;

import com.runninghi.adminpost.command.domain.aggregate.entity.AdminPost;
import com.runninghi.adminpost.command.domain.repository.AdminPostCommandRepository;
import com.runninghi.bookmark.command.application.controller.BookmarkCommandController;
import com.runninghi.bookmark.command.application.dto.request.CreateBookmarkRequest;
import com.runninghi.bookmark.command.application.dto.request.DeleteBookmarkRequest;
import com.runninghi.bookmark.command.application.service.BookmarkCommandService;
import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkVO;
import com.runninghi.bookmark.command.domain.repository.BookmarkCommandRepository;
 import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import com.runninghi.bookmarkfolder.command.domain.aggregate.vo.FolderMemberVO;
import com.runninghi.bookmarkfolder.command.domain.repository.FolderCommandRepository;
import com.runninghi.member.command.domain.aggregate.entity.Member;
import com.runninghi.member.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.member.command.domain.repository.MemberCommandRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BookmarkCommandControllerTests {

    private MockMvc mock;

    @Autowired
    BookmarkCommandController bookmarkCommandController;

    @Autowired
    BookmarkCommandRepository bookmarkCommandRepository;

    @Autowired
    FolderCommandRepository folderCommandRepository;

    @Autowired
    AdminPostCommandRepository adminPostCommandRepository;

    @MockBean
    BookmarkCommandService bookmarkCommandService;

    @Autowired
    private MemberCommandRepository memberCommandRepository;

    @Autowired
    private PasswordEncoder encoder;

    @BeforeEach
    @AfterEach
    void clear() {
        bookmarkCommandRepository.deleteAllInBatch();
        memberCommandRepository.deleteAllInBatch();
        folderCommandRepository.deleteAllInBatch();
        adminPostCommandRepository.deleteAllInBatch();

    }

    @BeforeEach
    void setup() {
        mock = MockMvcBuilders.standaloneSetup(bookmarkCommandController).build();
    }

    @Test
    @DisplayName("즐겨찾기 생성 컨트롤러 : success")
    void createBookmarkControllerTest() throws Exception {
        Member member = memberCommandRepository.save(Member.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("김철수")
                .nickname("qwe")
                .email("qwe@qwe.qw")
                .role(Role.USER)
                .status(true)
                .build());

        BookmarkFolder folder = BookmarkFolder.builder()
                .folderNo(999L)
                .folderName("테스트 폴더")
                .memberNoVO(new FolderMemberVO(member.getId()))
                .build();

        AdminPost memberPost = AdminPost.builder()
                .adminPostNo(999L)
                .adminPostTitle("테스트 게시글")
                .build();

        CreateBookmarkRequest request = new CreateBookmarkRequest(new BookmarkVO(folder.getFolderNo(), memberPost.getAdminPostNo()), member.getId());

        mock.perform(post("/bookmark/api/v1/bookmarks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("즐겨찾기 삭제 컨트롤러 : success")
    void deleteBookmarkControllerTest() throws Exception {

        BookmarkFolder folder = folderCommandRepository.save(BookmarkFolder.builder()
                .folderNo(999L)
                .folderName("폴더 수정")
                .memberNoVO(new FolderMemberVO(UUID.randomUUID()))
                .build());


        AdminPost memberPost = adminPostCommandRepository.save(AdminPost.builder()
                .adminPostNo(999L)
                .adminPostTitle("테스트 게시글")
                .build());

        DeleteBookmarkRequest request = new DeleteBookmarkRequest(new BookmarkVO(folder.getFolderNo(), memberPost.getAdminPostNo()));

        mock.perform(delete("/bookmark/api/v1/bookmarks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(request)))
                .andExpect(status().isOk());
    }
}
