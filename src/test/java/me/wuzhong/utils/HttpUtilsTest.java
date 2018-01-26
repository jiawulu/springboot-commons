package me.wuzhong.utils;

import com.alibaba.fastjson.JSON;

import me.wuzhong.utils.SpringContextsTest.Config;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author wuzhong on 2017/12/25.
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class})
public class HttpUtilsTest {

    @Autowired
    private HttpUtils httpUtils;

    @Test
    public void test() {
        Assert.assertNotNull(httpUtils);

        HttpEntity<String> post = httpUtils.post("http://www.baidu.com/", "123");

        System.out.println(JSON.toJSONString(post));

    }

    @Configuration
    @ComponentScan(basePackages = {"me.wuzhong.utils"})
    public static class Config {

    }

}