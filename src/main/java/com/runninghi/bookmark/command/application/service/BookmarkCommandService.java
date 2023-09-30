package com.runninghi.bookmark.command.application.service;

import com.runninghi.bookmark.command.application.dto.request.CreateBookmarkRequest;
import com.runninghi.bookmark.command.application.dto.request.DeleteBookmarkRequest;
import com.runninghi.bookmark.command.application.dto.response.BookmarkCommandResponse;
import com.runninghi.bookmark.command.application.dto.response.BookmarkDeleteResponse;
import com.runninghi.bookmark.command.domain.aggregate.entity.Bookmark;
import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkUserVO;
import com.runninghi.bookmark.command.domain.repository.BookmarkCommandRepository;
import com.runninghi.bookmark.command.domain.service.BookmarkCommandDomainService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BookmarkCommandService {

    private final BookmarkCommandRepository bookmarkRepository;
    private final BookmarkCommandDomainService domainService;

    @Transactional
    public BookmarkCommandResponse createBookmark(CreateBookmarkRequest bookmarkDTO) {

        //infraService.validatePostExist(bookmarkDTO.bookmarkVO().getPostNo());
        domainService.validateFolderExist(bookmarkDTO.bookmarkVO().getFolderNo());
        domainService.validateBookmarkExist(bookmarkDTO.bookmarkVO());

        Bookmark bookmark = bookmarkRepository.save(Bookmark.builder()
                .bookmarkVO(bookmarkDTO.bookmarkVO())
                .userNoVO(new BookmarkUserVO(bookmarkDTO.userNo()))
                .addDate(LocalDate.now())
                .build());

        return BookmarkCommandResponse.from(bookmark);
    }

    @Transactional
    public BookmarkDeleteResponse deleteBookmark(DeleteBookmarkRequest bookmarkDTO) {

        domainService.validateFolderExist(bookmarkDTO.bookmarkVO().getFolderNo());

        bookmarkRepository.deleteById(bookmarkDTO.bookmarkVO());

        return new BookmarkDeleteResponse(true);
    }

}
