package baekgwa.auctionservice.global.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

public record BaseResponse<T>(
        @JsonIgnore
        HttpStatus httpStatus,
        Boolean isSuccess,
        String message,
        int code,
        T data
) {

    public static <T> BaseResponse<T> ok (T data) {
        return new BaseResponse<>(
                BaseResponseCode.SUCCESS.getHttpStatus(),
                BaseResponseCode.SUCCESS.getIsSuccess(),
                BaseResponseCode.SUCCESS.getMessage(),
                BaseResponseCode.SUCCESS.getCode(),
                data
        );
    }

    public static BaseResponse<Void> ok() {
        return new BaseResponse<>(
                BaseResponseCode.SUCCESS.getHttpStatus(),
                BaseResponseCode.SUCCESS.getIsSuccess(),
                BaseResponseCode.SUCCESS.getMessage(),
                BaseResponseCode.SUCCESS.getCode(),
                null
        );
    }

    // 실패 응답 생성 팩토리 메서드
    public static BaseResponse<Void> fail(BaseResponseCode code) {
        return new BaseResponse<>(
                code.getHttpStatus(),
                code.getIsSuccess(),
                code.getMessage(),
                code.getCode(),
                null
        );
    }
}
