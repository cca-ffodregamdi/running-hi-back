package com.runninghi.keyword.query.application.service;

import com.runninghi.keyword.command.domain.aggregate.entity.Keyword;
import com.runninghi.keyword.query.application.dto.response.GetKeywordListResponse;
import com.runninghi.keyword.query.infrastructure.repository.KeywordQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeywordQueryService {

    private final KeywordQueryRepository keywordQueryRepository;

    public List<GetKeywordListResponse> getKeywordList() {
        List<Keyword> keywordListResponse = keywordQueryRepository.findAll();
        if (keywordListResponse.isEmpty()) {
            throw new NullPointerException("검색 결과가 없습니다.");
        }
        List<GetKeywordListResponse> keywordList = keywordListResponse
                .stream()
                .map(i -> new GetKeywordListResponse(i.getKeywordNo(), i.getKeywordName()))
                .collect(Collectors.toList());
        return keywordList;
    }

}
