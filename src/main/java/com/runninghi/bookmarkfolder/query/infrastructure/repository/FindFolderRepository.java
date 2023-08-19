package com.runninghi.bookmarkfolder.query.infrastructure.repository;

import com.runninghi.bookmarkfolder.command.domain.aggregate.entity.BookmarkFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FindFolderRepository extends JpaRepository<BookmarkFolder, Long> {

}
