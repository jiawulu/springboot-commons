package me.wuzhong.jackson;

import lombok.Data;

@Data
public class Car {

    private String band;

    private Integer id;

    private Double weight;

    private Wheel wheel;

    @Data
    public static class Wheel {

        private Integer size;

        private Integer color;

    }

}
