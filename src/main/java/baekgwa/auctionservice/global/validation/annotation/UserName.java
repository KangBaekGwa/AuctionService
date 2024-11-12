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
@Constraint(validatedBy = { })
@Retention(RetentionPolicy.RUNTIME)
@NotBlank(message = "이름은 필수값 입니다.")
@Size(min = 2, max = 15, message = "이름은 2글자 ~ 15글자 사이입니다.")
@Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "이름은 한글과 영문만 허용됩니다.")
public @interface UserName {
    String message() default "이름은 유효하지 않습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
