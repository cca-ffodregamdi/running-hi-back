package com.runninghi.bookmarkfolder.command.domain.aggregate.entity;


import com.runninghi.bookmarkfolder.command.application.dto.request.UpdateFolderRequest;
import com.runninghi.bookmarkfolder.command.domain.aggregate.vo.FolderMemberVO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "TBL_BOOKMARK_FOLDER")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class BookmarkFolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long folderNo;

    @Column
    private String folderName;

    @Embedded
    private FolderMemberVO memberNoVO;

    @Builder
    public BookmarkFolder(Long folderNo, String folderName, FolderMemberVO memberNoVO) {
        this.folderNo = folderNo;
        this.folderName = folderName;
        this.memberNoVO = memberNoVO;
    }

    public void update(UpdateFolderRequest folderRequest) {
        this.folderName = folderRequest.folderName();
    }

}
