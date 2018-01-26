package me.wuzhong.guava;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import org.junit.Test;

/**
 * @author wuzhong on 2017/12/24.
 * @version 1.0
 */
public class ListsTest<T, R> {

    @Test
    public void test() {

        ArrayList<Person> cars = Lists.newArrayList(new Person(1), new Person(2));

        List<Integer> collect = cars.stream().map(Person::getId).collect(Collectors.toList());

        System.out.println(collect);

        int[] ints = cars.stream().mapToInt(Person::getId).distinct().toArray();

        System.out.println(ints[0]);

        List<Integer> extract = extract(cars, Person::getId);
        System.out.println(extract);

    }

    public static <T, R> List<R> extract(List<T> collection, Function<? super T, R> mapper) {
        List<R> collect = collection.stream().map(mapper).collect(Collectors.toList());
        return collect;
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
