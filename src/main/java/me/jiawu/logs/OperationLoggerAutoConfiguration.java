package me.jiawu.logs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OperationLoggerAutoConfiguration {

    @Bean
    public OperationLogger operationLogger() {
        return new OperationLogger();
    }

}
