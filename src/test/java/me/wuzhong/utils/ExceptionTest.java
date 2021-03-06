package me.wuzhong.utils;

import me.wuzhong.utils.ExCatcher.Catchable;
import me.wuzhong.utils.ExCatcher.ResultConvertor;
import me.wuzhong.utils.result.Result;
import me.wuzhong.utils.ExceptionTest.Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author wuzhong on 2017/12/25.
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class})
public class ExceptionTest {

    @Autowired
    private AopService aopService;

    @Test
    public void test() {
        Result<Boolean> run = aopService.run();

        System.out.println(run);
    }

    @Configuration
    @ComponentScan(basePackages = {"me.wuzhong.utils"})
    @EnableAspectJAutoProxy
    public static class Config {

    }

    @Service
    public static class AopService {

        @Catchable(convertor = ResultConvertor.class)
        public Result<Boolean> run() {
            throw new RuntimeException("from test");
        }

    }

}
