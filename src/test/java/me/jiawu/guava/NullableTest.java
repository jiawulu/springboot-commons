package me.jiawu.guava;

import java.util.Optional;

import org.junit.Test;

/**
 * @author wuzhong on 2018/1/1.
 * @version 1.0
 */
public class NullableTest {

    @Test
    public void testOptional(){

        Optional<String> s = Optional.of("1");

        Optional<Object> o = Optional.ofNullable(null);

        System.out.println(s.get());
        System.out.println(o.isPresent());

        Object o1 = o.orElse("123456");
        System.out.println(o1);

    }

}
