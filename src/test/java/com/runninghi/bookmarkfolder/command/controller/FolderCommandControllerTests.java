package com.runninghi.bookmarkfolder.command.controller;

import com.runninghi.bookmarkfolder.command.application.controller.FolderCommandController;
import com.runninghi.bookmarkfolder.command.application.dto.request.CreateFolderRequest;
import com.runninghi.bookmarkfolder.command.application.dto.request.DeleteFolderRequest;
import com.runninghi.bookmarkfolder.command.application.dto.request.UpdateFolderRequest;
import com.runninghi.bookmarkfolder.command.application.service.BookmarkFolderCommandService;
import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import com.runninghi.bookmarkfolder.command.domain.aggregate.vo.FolderUserVO;
import com.runninghi.bookmarkfolder.command.domain.repository.BookmarkFolderRepository;
import com.runninghi.user.command.domain.aggregate.entity.User;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.user.command.domain.repository.UserCommandRepository;
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
public class FolderCommandControllerTests {

    @Autowired
    FolderCommandController folderCommandController;
    @MockBean
    BookmarkFolderCommandService bookmarkFolderCommandService;
    private MockMvc mock;
    @Autowired
    private BookmarkFolderRepository folderRepository;
    @Autowired
    private UserCommandRepository userCommandRepository;
    @Autowired
    private PasswordEncoder encoder;

    @BeforeEach
    @AfterEach
    void clear() {
        folderRepository.deleteAllInBatch();
        userCommandRepository.deleteAllInBatch();
    }

    @BeforeEach
    void setup() {
        mock = MockMvcBuilders.standaloneSetup(folderCommandController).build();
    }

    @Test
    @DisplayName("즐겨찾기 폴더 생성 컨트롤러 : success")
    void createFolderControllerTest() throws Exception {
        User user = userCommandRepository.save(User.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("김철수")
                .nickname("qwe")
                .email("qwe@qwe.qw")
                .role(Role.USER)
                .status(true)
                .build());

        CreateFolderRequest request = new CreateFolderRequest("폴더 생성 컨트롤러", new FolderUserVO(user.getId()));

        mock.perform(post("/api/v1/bookmark-folders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("즐겨찾기 폴더 수정 컨트롤러 : success")
    void updateCommentControllerTest() throws Exception {

        BookmarkFolder folder = BookmarkFolder.builder()
                .folderNo(999L)
                .folderName("폴더 수정")
                .userNo(new FolderUserVO(UUID.randomUUID()))
                .build();

        UpdateFolderRequest request = new UpdateFolderRequest(folder.getFolderNo(), "수정!!!", folder.getUserNo());

        mock.perform(put("/api/v1/bookmark-folders/" + request.folderNo())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("즐겨찾기 폴더 삭제 컨트롤러 : success")
    void deleteCommentControllerTest() throws Exception {

        BookmarkFolder folder = BookmarkFolder.builder()
                .folderNo(999L)
                .folderName("폴더 수정")
                .userNo(new FolderUserVO(UUID.randomUUID()))
                .build();

        DeleteFolderRequest request = new DeleteFolderRequest(folder.getFolderNo());

        mock.perform(delete("/api/v1/bookmark-folders/" + request.folderNo())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(request)))
                .andExpect(status().isOk());
    }

}
