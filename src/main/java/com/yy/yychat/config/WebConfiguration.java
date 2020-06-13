package com.yy.yychat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Value("${avatar-upload-path}")
    String avatarPath;

    @Value("${qrcode-upload-path}")
    String qrCodePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/avatars/**")
                .addResourceLocations("file:" + avatarPath);

        registry.addResourceHandler("/qrcodes/**")
                .addResourceLocations("file:" + qrCodePath);
    }
}
