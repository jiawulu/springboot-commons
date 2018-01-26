package me.wuzhong.utils;

import java.util.List;

import com.alibaba.fastjson.JSON;

import com.google.common.collect.Lists;
import org.slf4j.Logger;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wuzhong on 2017/12/24.
 * @version 1.0
 */
@Slf4j
public class LogUtils {

    /**
     * 快速输出日志，如果是对象，会被转换成jsonstring
     * @param format
     * @param arguments
     */
    public static void info(String format, Object... arguments){
        LogUtils.info(log,format,arguments);
    }

    /**
     * 快速输出日志，如果是对象，会被转换成jsonstring
     * @param logger
     * @param format
     * @param arguments
     */
    public static void info(Logger logger, String format, Object... arguments){
        if(arguments.length > 0){
            List<Object> objects = Lists.newArrayList(arguments);
            List<String> strings = StreamUtils.convertList(objects, one -> JSON.toJSONString(one, true));
            logger.info(format,strings);
        }else {
            logger.info(format, arguments);
        }
    }


}
