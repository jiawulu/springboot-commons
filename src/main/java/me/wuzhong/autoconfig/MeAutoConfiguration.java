package me.wuzhong.autoconfig;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//META-INF/spring.factories
// org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
//com.reger.dubbo.config.DubboAutoConfiguration
@Configuration
//满足条件才进行解析处理
@ConditionalOnClass({MeConfigurationService.class})
@EnableConfigurationProperties(MeConfig.class)
public class MeAutoConfiguration {

    @Resource
    private MeConfig meConfig;

    @Bean
    @ConditionalOnMissingBean(MeConfigurationService.class)
    //不配置就不进行自动配置bean
    @ConditionalOnProperty(name = "me.autoconfig", matchIfMissing = false)
    public MeConfigurationService authorResolver() {
        return new MeConfigurationService() {
            @Override
            public void first() {
                System.out.println(meConfig.getName());
            }
        };
    }
}