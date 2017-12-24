package me.jiawu.jackson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

@Data
public class Car2 {

    @JsonProperty("band_name")
    private String band;

    private Integer id;

    @JsonIgnore
    private Double weight;

    @JsonUnwrapped
    private Wheel wheel;

    @Data
    public static class Wheel {

        private Integer size;

        private Integer color;

    }

}