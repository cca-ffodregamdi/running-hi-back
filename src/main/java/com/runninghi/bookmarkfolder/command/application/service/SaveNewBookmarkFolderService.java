package com.runninghi.bookmarkfolder.command.application.service;

import com.runninghi.bookmarkfolder.command.application.dto.SaveFolderDTO;
import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import com.runninghi.bookmarkfolder.command.domain.repository.SaveFolderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class SaveNewBookmarkFolderService {

    private final SaveFolderRepository bookmarkFolderRepository;

    public SaveNewBookmarkFolderService(SaveFolderRepository bookmarkFolderRepository) {
        this.bookmarkFolderRepository = bookmarkFolderRepository;
    }

    @Transactional
    public BookmarkFolder saveNewBookmarkFolder(SaveFolderDTO folderDTO, Long userNo) {
        //userNo 받아옴

        if (folderDTO.getFolderName().length() > 20) {
            throw new IllegalArgumentException("폴더 제목이 20자를 초과하였습니다.");
        } else if (folderDTO.getFolderName().length() < 1) {
            throw new IllegalArgumentException("폴더 제목이 1자 미만입니다.");
        } else {
            BookmarkFolder newFolder = new BookmarkFolder(folderDTO.getFolderName(), userNo);
            return bookmarkFolderRepository.save(newFolder);
        }
    }


}
