package baekgwa.auctionservice.docs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import baekgwa.auctionservice.domain.authentication.dto.RequestAuthDto.SignUp;
import baekgwa.auctionservice.global.common.response.BaseResponseCode;
import baekgwa.auctionservice.integration.RestDocksSupport;
import baekgwa.auctionservice.domain.authentication.controller.AuthController;
import baekgwa.auctionservice.domain.authentication.dto.RequestAuthDto;
import baekgwa.auctionservice.domain.authentication.service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class AuthControllerDocumentTest extends RestDocksSupport {

    private final AuthService authService = Mockito.mock(AuthService.class);

    @Override
    protected Object initController() {
        return new AuthController(authService);
    }

    @DisplayName("[Docs] 로그인 API")
    @Test
    void loginUnion() throws Exception {
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
                .andExpect(jsonPath("$.data").doesNotExist())
                .andDo(document("authentication/loginUnion",
                        requestFields(
                                fieldWithPath("loginId").type(JsonFieldType.STRING)
                                        .description("로그인 아이디")
                                        .attributes(key("validity").value("로그인 아이디는 5자리 ~ 20자리 사이입니다.\n로그인 아이디는 영문(대소문자 구분)과 숫자만 허용합니다.")),
                                fieldWithPath("password").type(JsonFieldType.STRING)
                                        .description("로그인 패스워드")
                                        .attributes(key("validity").value("비밀번호는 8자리 ~ 20자리 사이 입니다.\n비밀번호는 특수문자를 반드시 포함하여야 합니다."))
                        ),
                        responseFields(
                                fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN)
                                        .description("성공 여부"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("응답 메세지"),
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("응답 코드"),
                                fieldWithPath("data").type(JsonFieldType.NULL)
                                        .description("응답 데이터")
                        )))
        ;
    }

    @DisplayName("[Docs] 회원가입 API")
    @Test
    void signupUnion() throws Exception {
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
                .andExpect(jsonPath("$.data").doesNotExist())
                .andDo(document("authentication/signUpUnion",
                        requestFields(
                                fieldWithPath("loginId").type(JsonFieldType.STRING)
                                        .description("로그인 아이디")
                                        .attributes(key("validity").value("로그인 아이디는 5자리 ~ 20자리 사이입니다.\n로그인 아이디는 영문(대소문자 구분)과 숫자만 허용합니다.")),
                                fieldWithPath("password").type(JsonFieldType.STRING)
                                        .description("로그인 패스워드")
                                        .attributes(key("validity").value("비밀번호는 8자리 ~ 20자리 사이 입니다.\n비밀번호는 특수문자를 반드시 포함하여야 합니다.")),
                                fieldWithPath("name").type(JsonFieldType.STRING)
                                        .description("이름")
                                        .attributes(key("validity").value("이름은 2글자 ~ 15글자 사이입니다.\n이름은 한글과 영문만 허용됩니다.")),
                                fieldWithPath("nickName").type(JsonFieldType.STRING)
                                        .description("닉네임")
                                        .attributes(key("validity").value("닉네임은 2글자 ~ 10글자 사이입니다.\n닉네임은 영문, 국문, 숫자만 허용합니다.")),
                                fieldWithPath("email").type(JsonFieldType.STRING)
                                        .description("이메일")
                                        .attributes(key("validity").value("잘못된 이메일 형식입니다.")),
                                fieldWithPath("phone").type(JsonFieldType.STRING)
                                        .description("전화 번호")
                                        .attributes(key("validity").value("전화번호는 8자리에서 11자리까지 입력해야 합니다.\n전화번호는 숫자만 허용됩니다."))
                        ),
                        responseFields(
                                fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN)
                                        .description("성공 여부"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("응답 메세지"),
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("응답 코드"),
                                fieldWithPath("data").type(JsonFieldType.NULL)
                                        .description("응답 데이터")
                        )))
        ;
    }

    private RequestAuthDto.Login createLoginData(String loginId, String password) {
        return RequestAuthDto.Login.builder()
                .loginId(loginId)
                .password(password)
                .build();
    }
}