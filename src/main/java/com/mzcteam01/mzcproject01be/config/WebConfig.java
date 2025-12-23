package com.mzcteam01.mzcproject01be.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.absolute-path}")
    private String FILE_ABSOLUTE_PATH;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String fullPath = FILE_ABSOLUTE_PATH;
        String resourceLocation = "file:///" + fullPath.replace("\\", "/");

        registry.addResourceHandler("/files/**")
                .addResourceLocations(resourceLocation);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 전체 경로에 CORS 적용
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
