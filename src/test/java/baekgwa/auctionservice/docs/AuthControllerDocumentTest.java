package baekgwa.auctionservice.docs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import baekgwa.auctionservice.global.common.response.BaseResponseCode;
import baekgwa.auctionservice.integration.RestDocksSupport;
import baekgwa.auctionservice.web.authentication.controller.AuthController;
import baekgwa.auctionservice.web.authentication.dto.RequestAuthDto;
import baekgwa.auctionservice.web.authentication.service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
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
        mockMvc.perform(MockMvcRequestBuilders.post(basePath + "/login")
                        .content(objectMapper.writeValueAsString(loginData))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(BaseResponseCode.SUCCESS.getIsSuccess()))
                .andExpect(jsonPath("$.message").value(BaseResponseCode.SUCCESS.getMessage()))
                .andExpect(jsonPath("$.code").value(BaseResponseCode.SUCCESS.getCode()))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andDo(document("authentication",
                        requestFields(
                                fieldWithPath("loginId").type(JsonFieldType.STRING)
                                        .description("로그인 아이디"),
                                fieldWithPath("password").type(JsonFieldType.STRING)
                                        .description("로그인 패스워드")
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