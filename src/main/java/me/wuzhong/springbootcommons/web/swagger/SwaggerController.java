package me.wuzhong.springbootcommons.web.swagger;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import me.wuzhong.logs.Loggable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuzhong on 2017/12/2.
 * @version 1.0
 */
@RestController
@RequestMapping(value = "api/swaggers")
@Api(tags = "swagger", value = "swagger api", description = "swagger api desc")
public class SwaggerController {

    @Loggable
    @GetMapping(value = "success")
    @ApiOperation("test success")
    public Response demo(Request request) {
        return new Response();
    }

    @Data
    public static class Request {

        @ApiParam(required = true, value = "query1")
        private long query1;

        @ApiParam(required = false, value = "query2")
        private long query2;

    }

    @Data
    @ApiModel(value = "demo response", description = "demo response", discriminator = "")
    public static class Response {
        @ApiModelProperty(value = "demo id")
        private long id;

        @ApiModelProperty(value = "demo name")
        private long name;
    }
}
