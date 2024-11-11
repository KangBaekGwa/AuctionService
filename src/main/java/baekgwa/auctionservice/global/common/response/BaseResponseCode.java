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

    FAIL(HttpStatus.BAD_REQUEST, Boolean.FALSE, 400, "요청 응답 실패");

    private final HttpStatus httpStatus;
    private final Boolean isSuccess;
    private final int code;
    private final String message;
}
