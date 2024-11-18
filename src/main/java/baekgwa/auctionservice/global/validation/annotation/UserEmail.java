package baekgwa.auctionservice.global.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { })
@Email(message = "잘못된 이메일 형식입니다.")
public @interface UserEmail {
    String message() default "유효하지 않은 이메일 입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
