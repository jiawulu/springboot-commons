package me.wuzhong.utils;

import com.alibaba.fastjson.JSON;

import org.junit.Test;

/**
 * @author wuzhong on 2017/12/24.
 * @version 1.0
 */
public class LogUtilsTest {

    @Test
    public void info() {

        String jsonString = JSON.toJSONString(1);
        System.out.println(jsonString);

        String jsonString1 = JSON.toJSONString(null);
        System.out.println(jsonString1);

        LogUtils.info("test 123 {}", 1,2);

        LogUtils.info("test 123 {} , {}", new Person(123), new Person(222));

    }

    public static class Person {
        public int id;

        public Person(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}