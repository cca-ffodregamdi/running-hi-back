package com.runninghi.keywordofpost.command.application.service;

import com.runninghi.keywordofpost.command.application.dto.request.KeywordOfPostCreateRequest;
import com.runninghi.keywordofpost.command.domain.aggregate.entity.KeywordOfPost;
import com.runninghi.keywordofpost.command.domain.aggregate.vo.AdminPostVO;
import com.runninghi.keywordofpost.command.domain.aggregate.vo.KeywordVO;
import com.runninghi.keywordofpost.command.domain.aggregate.vo.UserPostVO;
import com.runninghi.keywordofpost.command.domain.repository.KeywordOfPostCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class KeywordOfPostCommandService {

    private final KeywordOfPostCommandRepository keywordOfPostCommandRepository;

    public List<Long> createKeywordOfAdminPost(List<KeywordOfPostCreateRequest> requests) {

        try{
            List<KeywordOfPost> keywordOfPostList = requests.stream().map(
               request -> KeywordOfPost.builder()
                           .adminPostVO(new AdminPostVO(request.adminPostNo()))
                           .userPostVO(new UserPostVO(request.userPostNo()))
                           .keywordVO(new KeywordVO(request.keywordNo()))
                           .build()
            ).collect(Collectors.toList());
            return keywordOfPostCommandRepository.saveAll(keywordOfPostList)
                    .stream().map( k -> k.getKeywordVO().getKeywordNo()
                    ).collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new RuntimeException("저장에 실패하였습니다.");
        }
    }


    public List<Long> updateKeywordOfAdminPost(List<KeywordOfPostCreateRequest> request) {

        try {
            Long adminPostNo = request.get(0).adminPostNo();
            keywordOfPostCommandRepository.deleteKeywordOfPostByAdminPostNo(adminPostNo);
            return createKeywordOfAdminPost(request);
        } catch (RuntimeException e) {
            throw new RuntimeException("수정에 실패하였습니다.");
        }
    }

    public void deleteKeywordOfAdminPost(Long adminPostNo) {
        try {
            keywordOfPostCommandRepository.deleteKeywordOfPostByAdminPostNo(adminPostNo);
        } catch (RuntimeException e) {
            throw new RuntimeException("삭제에 실패하였습니다.");
        }
    }
}
