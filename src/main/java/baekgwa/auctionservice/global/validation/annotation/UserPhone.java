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
@Size(min = 8, max = 11, message = "전화번호는 8자리에서 11자리까지 입력해야 합니다.")
@Pattern(regexp = "^[0-9]+$", message = "전화번호는 숫자만 허용됩니다.")
public @interface UserPhone {
    String message() default "유효하지 않은 전화번호 입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
