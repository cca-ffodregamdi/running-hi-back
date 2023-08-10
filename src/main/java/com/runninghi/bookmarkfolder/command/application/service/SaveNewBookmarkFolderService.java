package com.runninghi.bookmarkfolder.command.application.service;

import com.runninghi.bookmarkfolder.command.application.dto.SaveFolderDTO;
import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import com.runninghi.bookmarkfolder.command.domain.repository.BookmarkFolderRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SaveNewBookmarkFolderService{

    private final BookmarkFolderRepository bookmarkFolderRepository;

    public SaveNewBookmarkFolderService(BookmarkFolderRepository bookmarkFolderRepository) {
        this.bookmarkFolderRepository = bookmarkFolderRepository;
    }

    @Transactional
    public void saveNewBookmarkFolder(SaveFolderDTO folderDTO, Long userNo) {
        //userNo 받아옴

        if(folderDTO.getFolderName().length() > 20) {
            throw new IllegalArgumentException("폴더 제목이 20자를 초과하였습니다.");
        } else if(folderDTO.getFolderName().length() < 1){
            throw new IllegalArgumentException("폴더 제목이 1자 미만입니다.");
        } else {
            BookmarkFolder newFolder = new BookmarkFolder(folderDTO.getFolderName(), userNo);
            bookmarkFolderRepository.save(newFolder);
        }
    }



}
