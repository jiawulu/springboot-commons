package me.wuzhong.utils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author wuzhong on 2017/12/24.
 * @version 1.0
 */
public class StreamUtils {

    /**
     * 工具类，快速转换一个list
     * @param collection
     * @param mapper
     * @return
     */
    public static <T, R> List<R> convertList(List<T> collection, Function<? super T, R> mapper) {
        return collection.stream().map(mapper).collect(Collectors.toList());
    }

    /**
     * 工具类，快速转换一个map
     * @param collection
     * @param mapper
     * @return
     */
    public static <T, R> Map<R, T> convertMap(List<T> collection, Function<? super T, R> mapper) {
        return collection.stream().collect(Collectors.toMap(mapper, one -> one));
    }

}
