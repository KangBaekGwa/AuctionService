package baekgwa.auctionservice.global.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { })
@Size(min = 2, max = 10, message = "닉네임은 2글자 ~ 10글자 사이입니다.")
@Pattern(regexp = "^[a-zA-Z0-9가-힣]+$", message = "닉네임은 영문, 국문, 숫자만 허용합니다.")
public @interface UserNickName {
    String message() default "유효하지 않은 닉네임 입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
