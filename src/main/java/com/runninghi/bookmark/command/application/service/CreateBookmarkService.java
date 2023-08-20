package com.runninghi.bookmark.command.application.service;

import com.runninghi.bookmark.command.application.dto.request.CreateBookmarkRequest;
import com.runninghi.bookmark.command.domain.aggregate.entity.Bookmark;
import com.runninghi.bookmark.command.domain.repository.BookmarkRepository;
import com.runninghi.bookmark.command.domain.service.BookmarkDomainService;
import com.runninghi.bookmark.command.infrastructure.service.BookmarkInfraService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CreateBookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final BookmarkDomainService domainService;

    @Transactional
    public Bookmark createBookmark(CreateBookmarkRequest bookmarkDTO) {

        //infraService.validatePostExist(bookmarkDTO.bookmarkVO().getPostNo());
        domainService.validateFolderExist(bookmarkDTO.bookmarkVO().getFolderNo());

        return bookmarkRepository.save(Bookmark.builder()
                .bookmarkVO(bookmarkDTO.bookmarkVO())
                .userNo(bookmarkDTO.userNo())
                .addDate(LocalDate.now())
                .build());
    }

}
