package baekgwa.auctionservice.web.apitest;

import baekgwa.auctionservice.global.common.exception.CustomException;
import baekgwa.auctionservice.global.common.response.BaseResponse;
import baekgwa.auctionservice.global.common.response.BaseResponseCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/success/nodata")
    private BaseResponse<Void> testApi() {
        return BaseResponse.ok();
    }

    @GetMapping("/success/data")
    private BaseResponse<String> testApi2() {
        String data = "테스트 데이터";
        return BaseResponse.ok(data);
    }

    @GetMapping("/fail")
    private BaseResponse<String> testApi3() {
        throw new CustomException(BaseResponseCode.FAIL);
    }
}
