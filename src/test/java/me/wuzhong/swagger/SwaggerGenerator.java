package me.wuzhong.swagger;

import java.net.URL;
import java.nio.file.Paths;

import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import me.wuzhong.springbootcommons.web.swagger.SwaggerController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wuzhong on 2018/1/26.
 * @version 1.0
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerGenerator {

    //@Autowired
    //private SwaggerController swaggerController;

    @Test
    public void generate() throws Exception {

        //输出markdown格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
            .withMarkupLanguage(MarkupLanguage.MARKDOWN)
            .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:8080/v2/api-docs"))
            .withConfig(config)
            .build()
            .toFolder(Paths.get("src/docs/md"));

    }
}
