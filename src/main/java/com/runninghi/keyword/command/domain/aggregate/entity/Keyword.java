package com.runninghi.keyword.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Table(name = "TBL_KEYWORD")
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keywordNo;

    @Column
    private String keywordName;

    @Builder
    public Keyword(Long keywordNo, String keywordName) {
        this.keywordNo = keywordNo;
        this.keywordName = keywordName;
    }

    public void update (String keywordName) {
        this.keywordName = keywordName;
    }

}
