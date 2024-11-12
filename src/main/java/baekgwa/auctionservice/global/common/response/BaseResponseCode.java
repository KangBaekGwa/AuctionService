package baekgwa.auctionservice.global.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BaseResponseCode {

    SUCCESS(HttpStatus.OK, Boolean.TRUE, 200, "요청 응답 성공"),

    //1000 ~ 2000
    //오류 종류 : 회원 서비스 오류
    FIND_USER_ERROR_NOT_FIND(HttpStatus.BAD_REQUEST, Boolean.FALSE, 1000, "잘못된 회원 정보"),

    //9000 ~ 9999
    //오류 종류 : 공통 에러
    VALIDATION_FAIL_ERROR(HttpStatus.BAD_REQUEST, Boolean.FALSE, 9000, "(exception error 메세지에 따름)"),
    FAIL(HttpStatus.BAD_REQUEST, Boolean.FALSE, 400, "요청 응답 실패");

    private final HttpStatus httpStatus;
    private final Boolean isSuccess;
    private final int code;
    private final String message;
}
