package com.runninghi.keyword.query.application.service;

import com.runninghi.common.handler.feedback.customException.NotFoundException;
import com.runninghi.keyword.command.domain.aggregate.entity.Keyword;
import com.runninghi.keyword.query.application.dto.response.FindKeywordResponse;
import com.runninghi.keyword.query.infrastructure.repository.KeywordQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeywordQueryService {

    private final KeywordQueryRepository keywordQueryRepository;

    @Transactional
    public List<FindKeywordResponse> findKeywordList() {
        List<Keyword> keywordListResponse = keywordQueryRepository.findAll();
        if (keywordListResponse.isEmpty()) {
            throw new NullPointerException("검색 결과가 없습니다.");
        }
        List<FindKeywordResponse> keywordList = keywordListResponse
                .stream()
                .map(i -> new FindKeywordResponse(i.getKeywordNo(), i.getKeywordName()))
                .collect(Collectors.toList());
        return keywordList;
    }

    @Transactional
    public FindKeywordResponse findKeyword(String keywordName) {

        return keywordQueryRepository.findKeywordByKeywordName(keywordName)
                .map(FindKeywordResponse::from)
                .orElseThrow(
                        () -> new NotFoundException("일치하는 키워드가 없습니다.")
                );
    }

}
