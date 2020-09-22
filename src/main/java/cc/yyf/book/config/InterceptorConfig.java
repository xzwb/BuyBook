package cc.yyf.book.config;

import cc.yyf.book.interceptor.JWTInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    /**
     * 拦截器拦截/u开头的路径
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor()).addPathPatterns("/u/**");
    }

    /**
     * JWT拦截器
     * @return
     */
    @Bean
    public JWTInterceptor jwtInterceptor() {
        return new JWTInterceptor();
    }

    // 配置静态资源不被拦截
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/dist/")
                .addResourceLocations("classpath:/public/")
                .addResourceLocations("file:/home/xzwb/BuyBook/public/")
                .addResourceLocations("file:/public/")
                .addResourceLocations("file:/home/xzwb/public/");
        super.addResourceHandlers(registry);
    }
}
