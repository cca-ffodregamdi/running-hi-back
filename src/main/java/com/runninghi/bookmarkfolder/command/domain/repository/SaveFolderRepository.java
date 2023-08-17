package com.runninghi.bookmarkfolder.command.domain.repository;

import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveFolderRepository extends JpaRepository<BookmarkFolder, Long> {

}
