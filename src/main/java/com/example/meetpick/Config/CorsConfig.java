package com.example.meetpick.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns(
                                "http://localhost:*",
                                "http://localhost:5174",
                                "https://meetpick-tau.vercel.app",
                                "https://meetpick-fe.vercel.app",
                                "https://meetpick-fe-*.vercel.app",
                                "https://meetpick_fe.vercel.app",
                                "https://toy-project-*-hyeonji-s-projects.vercel.app",
                                "https://seoyeonlee.site",
                                "https://www.seoyeonlee.site",
                                "https://*.seoyeonlee.site"
                        )
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
