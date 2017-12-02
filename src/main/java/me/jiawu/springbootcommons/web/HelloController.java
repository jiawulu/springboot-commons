package me.jiawu.springbootcommons.web;

import me.jiawu.autoconfig.MeConfigurationService;
import me.jiawu.autoconfig.MeConfig;
import me.jiawu.logs.Loggable;
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
public class HelloController {

    @Autowired
    private MeConfig meConfig;

    @Autowired(required = false)
    private MeConfigurationService autoConfigurationService;

    @Loggable
    @GetMapping( value = "success")
    public String success(){
        if (null != autoConfigurationService) {autoConfigurationService.first();}
        return "success 222 3333 !!!!" + meConfig.getName();
    }

    @GetMapping( value = "fail")
    public String fail(){
        throw new RuntimeException("failed");
    }



}
