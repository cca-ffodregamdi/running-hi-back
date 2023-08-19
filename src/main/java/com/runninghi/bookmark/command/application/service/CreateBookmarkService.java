package com.runninghi.bookmark.command.application.service;

import com.runninghi.bookmark.command.domain.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateBookmarkService {

    @Autowired
    BookmarkRepository bookmarkRepository;


}
