package me.wuzhong.utils;

import java.util.Optional;

/**
 * @author wuzhong on 2018/1/1.
 * @version 1.0
 */
public class NumberPredicates {

    public static boolean isGreaterThan(long compareToValue, Long... compareFromValues) {
        for (Long value : compareFromValues) {
            if (Optional.ofNullable(value).orElse(0L) <= compareToValue) {
                return false;
            }
        }
        return true;
    }

    public static boolean containsBitValue(int value, int... bits) {
        for (int bit : bits) {
            if ((value & bit) != bit) {
                return false;
            }
        }
        return true;
    }

    public static boolean containsBitValue(long value, long... bits) {
        for (long bit : bits) {
            if ((value & bit) != bit) {
                return false;
            }
        }
        return true;
    }

}
