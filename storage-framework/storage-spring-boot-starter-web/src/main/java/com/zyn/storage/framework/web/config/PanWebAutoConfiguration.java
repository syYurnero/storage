package com.zyn.storage.framework.web.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.zyn.storage.framework.web.handle.GlobalExceptionHandler;

@EnableFeignClients(basePackages = "com.zyn.*")
public class PanWebAutoConfiguration{

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
