package com.runninghi.bookmarkfolder.command.application.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "즐겨찾기 폴더 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/bookmark-folders")
public class FolderCommandController {
}
