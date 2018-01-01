package me.jiawu.utils;

import java.util.Optional;

import org.apache.commons.lang.math.NumberUtils;

/**
 * @author wuzhong on 2018/1/1.
 * @version 1.0
 */
public class ConvertUtils {

    public static long toLong(String value, long defaultValue) {
        return NumberUtils.toLong(value, defaultValue);
    }

    public static int toInteger(String value, int defaultValue){
        return NumberUtils.toInt(value,defaultValue);
    }

    public static long toLong(Long value){
        return Optional.ofNullable(value).orElse(0L);
    }



}
