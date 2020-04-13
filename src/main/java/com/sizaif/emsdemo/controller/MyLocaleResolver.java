package com.sizaif.emsdemo.controller;

import org.springframework.ui.Model;
import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

// 中英文切换,更改解析Encoding
public class MyLocaleResolver implements LocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        //获取请求参数
        String language = httpServletRequest.getParameter("lang");

        Locale locale = Locale.getDefault();// 取得默认

        //若不为空,则使用参数
        if(!StringUtils.isEmpty(language)) {
            //zh_CN Or en_US
            String[] split = language.split("_");
            locale = new Locale(split[0], split[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
