package baekgwa.auctionservice.global.common.exception;

import baekgwa.auctionservice.global.common.response.BaseResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {

    private final BaseResponseCode code;
}
