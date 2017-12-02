package me.jiawu.logs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Loggable {

    boolean onArgs() default true;

    boolean onResult() default true;

    boolean onThrowable() default true;

    boolean onThrowableOnly() default false;
}
