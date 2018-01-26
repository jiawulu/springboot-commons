package me.wuzhong.jackson;

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

    /**
     * fastjson: https://github.com/alibaba/fastjson/wiki/JSONField_unwrapped_cn
     */
    @JsonUnwrapped
    private Wheel wheel;

    @Data
    public static class Wheel {

        private Integer size;

        private Integer color;

    }

}
