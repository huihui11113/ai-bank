package com.life.bank.palm.web.config;


import com.life.bank.palm.web.interceptor.WebUserRequestContextInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @desc: 这块需要注意拦截器的顺序
 */
@Configuration
public class WebRequestConfig implements WebMvcConfigurer {
    @Autowired
    private WebUserRequestContextInterceptor webUserRequestContextInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webUserRequestContextInterceptor);
    }



    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .maxAge(3600)
                .allowCredentials(true);
    }


}
