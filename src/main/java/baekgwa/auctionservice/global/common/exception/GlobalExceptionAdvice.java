package baekgwa.auctionservice.global.common.exception;

import baekgwa.auctionservice.global.common.response.BaseResponse;
import baekgwa.auctionservice.global.common.response.BaseResponseCode;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * 사용자 설정 Exception 발생
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customException(CustomException e) {
        BaseResponse<Void> response = BaseResponse.fail(e.getCode());
        return new ResponseEntity<>(response, response.httpStatus());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> validationException(BindException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        StringBuilder message = new StringBuilder();
        for (ObjectError allError : allErrors) {
            if(!message.isEmpty()){
                message.append("\n");
            }
            message.append(allError.getDefaultMessage());
        }

        BaseResponse<?> response = new BaseResponse<>(
                BaseResponseCode.VALIDATION_FAIL_ERROR.getHttpStatus(),
                BaseResponseCode.VALIDATION_FAIL_ERROR.getIsSuccess(),
                message.toString(),
                BaseResponseCode.VALIDATION_FAIL_ERROR.getCode(),
                null);

        return new ResponseEntity<>(response, response.httpStatus());
    }
}
