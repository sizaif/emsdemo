package com.sizaif.emsdemo.Configurations;


import com.sizaif.emsdemo.controller.MyLocaleResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Component
public class MyMvcConfig implements WebMvcConfigurer {

    // 地址栏重定向
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("production/login");
//        registry.addViewController("/usr/login/lang").setViewName("production/login");
//        registry.addViewController("/index").setViewName("login");

        registry.addViewController("/home").setViewName("production/index");

        registry.addViewController("/tables_dynamic.html").setViewName("production/tables_dynamic");
//        registry.addViewController("/nav.html").setViewName("nav");

        //用户表管理
//        registry.addViewController("/admin_user/user_list").setViewName("production/Admin/user");
    }
    //    接入虚拟路径（解决重启服务器才显示图片的问题）
    // 拦截本地路径
    @Value("${web.upload-path}")
    private String path;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        // addResourceHandler: （ 存储图片的虚拟路径，在 static 目录下的 picture 文件夹，用于存储上传图片）
        // addResourceLocations: （ file: + 存储图片的路径）
        registry.addResourceHandler("/images/**").addResourceLocations("file:" +path );
    }
    // 中英文切换
    @Bean
    public LocaleResolver localeResolver(){
        return  new MyLocaleResolver();
    }
}
