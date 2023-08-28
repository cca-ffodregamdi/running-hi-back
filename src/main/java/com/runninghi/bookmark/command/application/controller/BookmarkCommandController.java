package com.runninghi.bookmark.command.application.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "즐겨찾기 Command API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/bookmark")
public class BookmarkCommandController {

}
