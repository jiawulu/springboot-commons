package me.wuzhong.apache;

import java.util.Optional;

import org.apache.commons.lang.math.NumberUtils;
import org.junit.Test;

/**
 * @author wuzhong on 2018/1/1.
 * @version 1.0
 */
public class NumblerUtilsTest {


    @Test
    public void test(){

        Long a = null;

        long l = NumberUtils.toLong("12ab", 10L);
        System.out.println(l);

        //Longs

        //Numbers
        Long aLong = Optional.ofNullable(a).orElse(0L);
        System.out.println(aLong);
    }
}
