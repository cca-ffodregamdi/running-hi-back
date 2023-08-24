package com.runninghi.bookmarkfolder.command.controller;

import com.runninghi.bookmarkfolder.command.application.controller.FolderCommandController;
import com.runninghi.bookmarkfolder.command.application.dto.request.CreateFolderRequest;
import com.runninghi.bookmarkfolder.command.domain.repository.BookmarkFolderRepository;
import com.runninghi.user.command.domain.aggregate.entity.User;
import com.runninghi.user.command.domain.aggregate.entity.enumtype.Role;
import com.runninghi.user.command.domain.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class FolderCommandControllerTests {

    private MockMvc mock;

    @Autowired
    FolderCommandController folderCommandController;

    @Autowired
    private BookmarkFolderRepository folderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @BeforeEach
    @AfterEach
    void clear() {
        folderRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @BeforeEach
    void setup() {
        mock = MockMvcBuilders.standaloneSetup(folderCommandController).build();
    }

    @Test
    @DisplayName("폴더 생성 컨트롤러 : success")
    void testCreateFolder() throws Exception{
        User user = userRepository.save(User.builder()
                .account("qwerty1234")
                .password(encoder.encode("1234"))
                .name("김철수")
                .nickname("qwe")
                .email("qwe@qwe.qw")
                .role(Role.USER)
                .status(true)
                .build());

        CreateFolderRequest request = new CreateFolderRequest("test folder", user.getId());

        System.out.println(String.valueOf(request));

        mock.perform(post("/api/v1/bookmark-folders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(request)))
                .andExpect(status().isOk());
    }
}
