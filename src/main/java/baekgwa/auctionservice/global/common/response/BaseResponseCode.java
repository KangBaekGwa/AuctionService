package baekgwa.auctionservice.global.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BaseResponseCode {

    SUCCESS(HttpStatus.OK, Boolean.TRUE, 200, "요청 응답 성공"),

    //1000 ~ 2000
    //오류 종류 : 인증/인가 서비스 오류
    AUTHENTICATION_LOGIN_FAIL(HttpStatus.BAD_REQUEST, Boolean.FALSE, 1000, "잘못된 회원 정보 입니다."),

    //9000 ~ 9999
    //오류 종류 : 공통 에러
    VALIDATION_FAIL_ERROR(HttpStatus.BAD_REQUEST, Boolean.FALSE, 9000, "(exception error 메세지에 따름)"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, Boolean.FALSE, 9001, "요청 권한이 부족합니다."),
    FAIL(HttpStatus.BAD_REQUEST, Boolean.FALSE, 400, "요청 응답 실패"),
    ;

    private final HttpStatus httpStatus;
    private final Boolean isSuccess;
    private final int code;
    private final String message;
}
