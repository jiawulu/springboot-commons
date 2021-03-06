package me.wuzhong;

import com.spring4all.swagger.EnableSwagger2Doc;
import me.wuzhong.springbootcommons.WebErrorAdvisor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableSwagger2Doc
@SpringBootApplication
//@EnableAutoConfiguration
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
//@MapperScan("cn.jiawu.dal")
public class SpringbootCommonsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCommonsApplication.class, args);
	}

	@Bean
	public WebErrorAdvisor webErrorAdvisor() {
		return new WebErrorAdvisor();
	}


	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**");
			}
		};
	}

}
