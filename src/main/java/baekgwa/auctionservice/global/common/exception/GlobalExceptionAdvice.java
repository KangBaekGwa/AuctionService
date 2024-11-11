package baekgwa.auctionservice.global.common.exception;

import baekgwa.auctionservice.global.common.response.BaseResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * 사용자 설정 Exception 발생
     */
    @ExceptionHandler(CustomException.class)
    public BaseResponse<?> customException(CustomException e) {
        return BaseResponse.fail(e.getCode());
    }
}
