package vn.shopttcn.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

	// Cấu hình resourceHandlers để đ�?c các file tĩnh (css, js, image...)
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/");
		registry.addResourceHandler("/upload/**").addResourceLocations("/upload/");
		// addResourceLocations: real path
		// addResourceHandler: tên sử dụng để dẫn đến các file trong real path
	}

}

//DD
