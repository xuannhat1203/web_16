package com.data.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.Map;
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
        "com.data.controller",
        "com.data.service",
        "com.data.repository",
})
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public SpringResourceTemplateResolver springResourceTemplateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    @Bean
    public SpringTemplateEngine springTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(springResourceTemplateResolver());
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setTemplateEngine(springTemplateEngine());
        thymeleafViewResolver.setCharacterEncoding("UTF-8");
        thymeleafViewResolver.setForceContentType(true);
        return thymeleafViewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("/WEB-INF/css/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("/WEB-INF/js/");
        registry.addResourceHandler("/image/**")
                .addResourceLocations("/WEB-INF/image/");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("/static/");
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///C:/Users/xuann/IdeaProjects/SS16/src/main/webapp/WEB-INF/uploads/");
    }
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver common = new CommonsMultipartResolver();
        common.setMaxUploadSize(52428800);
        return common;
    }


    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = ObjectUtils.asMap(
                "cloud_name", "dfwuftmjf",
                "api_key", "659859734482993",
                "api_secret", "4VzlV5TT3rfz64oG7E1PTHkhzE4"
        );
        return new Cloudinary(config);
    }


}
