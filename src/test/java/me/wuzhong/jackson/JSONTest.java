package me.wuzhong.jackson;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.wuzhong.jackson.Car.Wheel;
import org.junit.Test;

/**
 * @author wuzhong on 2017/12/24.
 * @version 1.0
 *
 * @link http://www.baeldung.com/jackson-annotations
 *
 */
public class JSONTest {

    @Test
    public void testJSONOutput() throws JsonProcessingException {

        Car car = new Car();
        car.setBand("audi");
        car.setWheel(new Wheel());

        ObjectMapper objectMapper = new ObjectMapper();
        //一定要第一步就去设置，晚了就没有效果了
        objectMapper.setSerializationInclusion(Include.NON_NULL);

        String valueAsString = objectMapper.writeValueAsString(car);
        System.out.println(valueAsString);

        String string = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(car);
        System.out.println(string);


        String string2 = objectMapper.writeValueAsString(car);
        System.out.println(">>>");
        System.out.println(string2);

    }


    @Test
    public void testJSONOutput2() throws JsonProcessingException {

        Car2 car = new Car2();
        car.setBand("audi");
        car.setId(10);
        car.setWeight(10.0D);
        car.setWheel(new Car2.Wheel());
        car.getWheel().setSize(10);


        ObjectMapper objectMapper = new ObjectMapper();
        //一定要第一步就去设置，晚了就没有效果了
        objectMapper.setSerializationInclusion(Include.NON_NULL);

        String valueAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(car);
        System.out.println(valueAsString);

    }


    @Test
    public void testOutput() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        //一定要第一步就去设置，晚了就没有效果了
        objectMapper.setSerializationInclusion(Include.NON_NULL);

        Car2 car2 = objectMapper.readValue("{\n"
                                                  + "  \"id\" : 10,\n"
                                                  + "  \"size\" : 10,\n"
                                                  + "  \"band_name\" : \"audi\"\n"
                                                  + "}", Car2.class);

        System.out.println(car2.getWheel().getSize());

    }


}
