package com.sizaif.emsdemo.Configurations;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;


@Configuration
public class MyErrorPageConfig {

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                // 对嵌入式servlet容器的配置
                // factory.setPort(8081);
                /* 注意：new ErrorPage(stat, path);中path必须是页面名称，并且必须“/”开始。
                    底层调用了String.java中如下方法：
                    public boolean startsWith(String prefix) {
                        return startsWith(prefix, 0);
                    }*/
                ErrorPage errorPage400 = new ErrorPage(HttpStatus.BAD_REQUEST,
                        "/error_400");
                ErrorPage errorPage401 = new ErrorPage(HttpStatus.UNAUTHORIZED,
                        "/error_401");
                ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND,
                        "/error_404");
                ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,
                        "/error_500");
                ErrorPage errorPage403 = new ErrorPage(HttpStatus.FORBIDDEN,
                        "/error_403");
                factory.addErrorPages(errorPage400, errorPage404,
                        errorPage500,errorPage403,errorPage401);
            }
        };
    }

}
