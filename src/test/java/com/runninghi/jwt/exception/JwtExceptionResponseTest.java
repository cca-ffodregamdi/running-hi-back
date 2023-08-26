package com.runninghi.jwt.exception;

import com.runninghi.common.response.enumtype.ApiStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JwtExceptionResponseTest {
    private static final String EXPIRED_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlOWQ1YTE4MC04NDNhLTQwNTQtYThiZC0xZDFhODE2Yjc4ZjE6VVNFUiIsImlzcyI6InF3ZXJ0eTEyMzQiLCJpYXQiOjE2OTI1NTE1NzAsImV4cCI6MTY5MjU1MzM3MH0.KL93J_wXcGa_yYHAqLJTiXlRVHzTdYAlCzO20GilHdYE-hFFPQ1oqy7OTO17kLuSDxkGUYIU2sLVDltL4vfp5w";
    private static final String TAMPERED_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmMGY3YmZkNS0zYmMxLTRmYTctYTkzMi03NjMyNDRjMzkzYjk6QURNSU4iLCJpc3MiOiJjb2xhYmVhcjc1NCIsImlhdCI6MTY4NTAyNDMxM30.f0Puq1FE-68kvjGnejs_wjKdWdfQLWiHqHQBWi1INUc";
    private static final String MALFORMED_TOKEN = "1234567890";

    private final MockMvc mock;

    @Autowired
    public JwtExceptionResponseTest(MockMvc mock) {
        this.mock = mock;
    }

    private MockHttpServletRequestBuilder request() {
        return get("/api/vi/admin/users").contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @DisplayName("토큰 요청 테스트 : 토큰 없을 경우 접근거부")
    void doesNotExistTokenTest() throws Exception {
        // given
        // when
        ResultActions actions = mock.perform(request());
        // then
        actions.andExpect(status().isForbidden())
                .andExpect(jsonPath("$.status").value(ApiStatus.ERROR.name()))
                .andExpect(jsonPath("$.message").value("접근이 거부되었습니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @DisplayName("토큰 요청 테스트 : 만료된 토큰인 경우 권한제한")
    void expiredTokenTest() throws Exception {
        // given
        // when
        ResultActions actions = mock.perform(request().header(HttpHeaders.AUTHORIZATION, "Bearer " + EXPIRED_TOKEN));
        // then
        actions.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(ApiStatus.ERROR.name()))
                .andExpect(jsonPath("$.message").value("토큰이 만료되었습니다. 다시 로그인해주세요."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @DisplayName("토큰 요청 테스트 : 변조된 토큰인 경우 권한제한")
    void tamperedTokenTest() throws Exception {
        // given
        // when
        ResultActions actions = mock.perform(request().header(HttpHeaders.AUTHORIZATION, "Bearer " + TAMPERED_TOKEN));
        // then
        actions.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(ApiStatus.ERROR.name()))
                .andExpect(jsonPath("$.message").value("토큰이 유효하지 않습니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @DisplayName("토큰 요청 테스트 : 잘못된 토큰인 경우 권한제한")
    void invalidTokenTest() throws Exception {
        // given
        // when
        ResultActions actions = mock.perform(request().header(HttpHeaders.AUTHORIZATION, "Bearer " + MALFORMED_TOKEN));
        // then
        actions.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(ApiStatus.ERROR.name()))
                .andExpect(jsonPath("$.message").value("올바르지 않은 토큰입니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}
