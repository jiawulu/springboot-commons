package me.wuzhong.utils;

import java.util.List;

import com.google.common.collect.Lists;
import org.junit.Test;

/**
 * @author wuzhong on 2017/12/24.
 * @version 1.0
 */
public class StreamUtilsTest {

    @Test
    public void test() {

        List<Person> cars = Lists.newArrayList(new Person(1), new Person(2));

        System.out.println(StreamUtils.convertList(cars,Person::getId));

        System.out.println(StreamUtils.convertMap(cars,Person::getId));

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