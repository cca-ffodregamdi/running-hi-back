package com.runninghi.keyword.query.application.service;

import com.runninghi.keyword.command.domain.aggregate.entity.Keyword;
import com.runninghi.keyword.query.application.dto.response.GetKeywordListResponse;
import com.runninghi.keyword.query.infrastructure.repository.QueryKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryKeywordService {

    private final QueryKeywordRepository queryKeywordRepository;

    public List<GetKeywordListResponse> getKeywordList() {
        List<Keyword> keywordListResponse = queryKeywordRepository.findAll();
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
