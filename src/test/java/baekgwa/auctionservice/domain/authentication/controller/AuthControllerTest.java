package baekgwa.auctionservice.domain.authentication.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import baekgwa.auctionservice.domain.authentication.dto.RequestAuthDto.SignUp;
import baekgwa.auctionservice.global.common.response.BaseResponseCode;
import baekgwa.auctionservice.integration.MockControllerTestSupporter;
import baekgwa.auctionservice.domain.authentication.dto.RequestAuthDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

class AuthControllerTest extends MockControllerTestSupporter {

    @DisplayName("통합 로그인 성공 테스트")
    @Test
    void loginUnionTest1() throws Exception {
        // given
        RequestAuthDto.Login loginData = createLoginData("test1", "!test1234");

        // when //then
        mockMvc.perform(post(basePath + "/login")
                        .content(objectMapper.writeValueAsString(loginData))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(BaseResponseCode.SUCCESS.getIsSuccess()))
                .andExpect(jsonPath("$.message").value(BaseResponseCode.SUCCESS.getMessage()))
                .andExpect(jsonPath("$.code").value(BaseResponseCode.SUCCESS.getCode()))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @DisplayName("로그인 아이디는 한글이 불가능 합니다.")
    @Test
    void loginUnionTest2() throws Exception {
        // given
        RequestAuthDto.Login loginData = createLoginData("한글금지입니다!", "!test1234");

        // when //then
        mockMvc.perform(post(basePath + "/login")
                        .content(objectMapper.writeValueAsString(loginData))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(BaseResponseCode.VALIDATION_FAIL_ERROR.getIsSuccess()))
                .andExpect(jsonPath("$.message").value("로그인 아이디는 영문(대소문자 구분)과 숫자만 허용합니다."))
                .andExpect(jsonPath("$.code").value(BaseResponseCode.VALIDATION_FAIL_ERROR.getCode()))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @DisplayName("로그인 아이디는 5글자 이상 입니다.")
    @Test
    void loginUnionTest3() throws Exception {
        // given
        RequestAuthDto.Login loginData = createLoginData("test", "!test1234");

        // when //then
        mockMvc.perform(post(basePath + "/login")
                        .content(objectMapper.writeValueAsString(loginData))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(BaseResponseCode.VALIDATION_FAIL_ERROR.getIsSuccess()))
                .andExpect(jsonPath("$.message").value("로그인 아이디는 5자리 ~ 20자리 사이입니다."))
                .andExpect(jsonPath("$.code").value(BaseResponseCode.VALIDATION_FAIL_ERROR.getCode()))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @DisplayName("비밀번호는 8~20 자리 여야 합니다.")
    @Test
    void loginUnionTest4() throws Exception {
        // given
        RequestAuthDto.Login loginData = createLoginData("test1", "test!");

        // when //then
        mockMvc.perform(post(basePath + "/login")
                        .content(objectMapper.writeValueAsString(loginData))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(BaseResponseCode.VALIDATION_FAIL_ERROR.getIsSuccess()))
                .andExpect(jsonPath("$.message").value("비밀번호는 8자리 ~ 20자리 사이 입니다."))
                .andExpect(jsonPath("$.code").value(BaseResponseCode.VALIDATION_FAIL_ERROR.getCode()))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @DisplayName("비밀번호는 특수문자를 반드시 포함하여야 합니다.")
    @Test
    void loginUnionTest5() throws Exception {
        // given
        RequestAuthDto.Login loginData = createLoginData("test1", "testtest");

        // when //then
        mockMvc.perform(post(basePath + "/login")
                        .content(objectMapper.writeValueAsString(loginData))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(BaseResponseCode.VALIDATION_FAIL_ERROR.getIsSuccess()))
                .andExpect(jsonPath("$.message").value("비밀번호는 특수문자를 반드시 포함하여야 합니다."))
                .andExpect(jsonPath("$.code").value(BaseResponseCode.VALIDATION_FAIL_ERROR.getCode()))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @DisplayName("회원가입을 진행 합니다.")
    @Test
    void signupUnionTest1() throws Exception {
        // given
        RequestAuthDto.SignUp singUpData = SignUp
                .builder()
                .loginId("loginId")
                .password("!password1234")
                .name("name")
                .nickName("nickName")
                .email("email@email.com")
                .phone("01011112222")
                .build();

        // when // then
        mockMvc.perform(post(basePath + "/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(singUpData)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(BaseResponseCode.SUCCESS.getIsSuccess()))
                .andExpect(jsonPath("$.message").value(BaseResponseCode.SUCCESS.getMessage()))
                .andExpect(jsonPath("$.code").value(BaseResponseCode.SUCCESS.getCode()))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @DisplayName("회원가입 데이터 중, 형식에 맞지 않은 데이터가 오면, 오류 메세지를 반환 합니다.")
    @Test
    void signupUnionTest2() throws Exception {
        // given
        RequestAuthDto.SignUp singUpData = SignUp
                .builder()
                .loginId("한글금지")
                .password("양식지켜")
                .name("1")
                .nickName("@")
                .email("email")
                .phone("010")
                .build();

        // when // then
        mockMvc.perform(post(basePath + "/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(singUpData)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(BaseResponseCode.VALIDATION_FAIL_ERROR.getIsSuccess()))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.code").value(BaseResponseCode.VALIDATION_FAIL_ERROR.getCode()))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    private RequestAuthDto.Login createLoginData(String loginId, String password) {
        return RequestAuthDto.Login.builder()
                .loginId(loginId)
                .password(password)
                .build();
    }
}