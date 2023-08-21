package com.runninghi.bookmark.query.application.service;

import com.runninghi.bookmark.command.domain.aggregate.entity.Bookmark;
import com.runninghi.bookmark.command.domain.repository.BookmarkRepository;
import com.runninghi.bookmark.query.application.dto.FindBookmarkRequest;
import com.runninghi.common.handler.feedback.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryBookmarkService {

    private final BookmarkRepository bookmarkRepository;
    @Transactional(readOnly=true)
    public Bookmark findBookmark(FindBookmarkRequest bookmarkDTO) {
        return bookmarkRepository.findById(bookmarkDTO.bookmarkVO())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 즐겨찾기 입니다."));
    }
}
