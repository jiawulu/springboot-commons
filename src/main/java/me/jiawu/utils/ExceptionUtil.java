package me.jiawu.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import lombok.extern.slf4j.Slf4j;
import me.jiawu.utils.result.Result;

/**
 * @author wuzhong on 2017/9/26.
 * @version 1.0
 */
@Slf4j
public class ExceptionUtil {

    public static String getDetail(Throwable ex) {
        StringWriter errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }

    public static Result wrapResult(Callable callable) {

        try {

            return Result.ok(callable.call());

        } catch (Throwable e) {

            return toExcResult(e);
        }

    }

    public static Result toExcResult(Throwable e) {

        //TODO
        return null;
    }

    public static interface Callable {

        Object call();

    }

}
