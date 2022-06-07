package com.tsunglin.tsunglin00.config.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenToAdminUser {

    /**
     * 當前用戶在request中的名字
     *
     * @return
     */
    String value() default "adminUser";

}
