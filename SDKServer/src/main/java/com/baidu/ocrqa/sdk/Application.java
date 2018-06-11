package com.baidu.ocrqa.sdk;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PreDestroy;
import javax.servlet.MultipartConfigElement;

@EnableScheduling
@Slf4j
@SpringBootApplication
public class Application {
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        Application.context = SpringApplication.run(Application.class, args);
    }

    @PreDestroy
    public void close() {
        Application.context.close();
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize("30MB");
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("50MB");
        
        return factory.createMultipartConfig();
    }
}
