package com.runninghi.bookmark.query.infrastructure.repository;

import com.runninghi.bookmark.command.domain.aggregate.entity.Bookmark;
import com.runninghi.bookmark.command.domain.aggregate.vo.BookmarkVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkQueryRepository extends JpaRepository<Bookmark, BookmarkVO> {

    List<Bookmark> findBookmarkByBookmarkVO_FolderNo(Long folderNo);
}
