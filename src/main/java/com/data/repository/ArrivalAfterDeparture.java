//package com.data.repository;
//
//import com.data.service.ArrivalAfterDepartureValidator;
//
//import javax.validation.Constraint;
//import javax.validation.Payload;
//import java.lang.annotation.*;
//
//@Constraint(validatedBy = ArrivalAfterDepartureValidator.class)
//@Target({ ElementType.TYPE })
//@Retention(RetentionPolicy.RUNTIME)
//@Documented
//public @interface ArrivalAfterDeparture {
//    String message() default "Thời gian đến phải sau thời gian khởi hành";
//    Class<?>[] groups() default {};
//    Class<? extends Payload>[] payload() default {};
//}
