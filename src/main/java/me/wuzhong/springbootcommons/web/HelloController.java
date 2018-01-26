package me.wuzhong.springbootcommons.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.wuzhong.autoconfig.MeConfigurationService;
import me.wuzhong.autoconfig.MeConfig;
import me.wuzhong.logs.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuzhong on 2017/12/2.
 * @version 1.0
 */
@RestController
@RequestMapping(value = "api/hellos")
@Api(tags = "hello", value = "hello api", description = "hello api desc")
public class HelloController {

    @Autowired
    private MeConfig meConfig;

    @Autowired(required = false)
    private MeConfigurationService autoConfigurationService;

    @Loggable
    @GetMapping( value = "success")
    @ApiOperation("test success")
    public String success(){
        if (null != autoConfigurationService) {autoConfigurationService.first();}
        return "success 222 3333 !!!!" + meConfig.getName();
    }

    @GetMapping( value = "fail")
    @ApiOperation("test fail")
    public String fail(){
        throw new RuntimeException("failed");
    }


}
