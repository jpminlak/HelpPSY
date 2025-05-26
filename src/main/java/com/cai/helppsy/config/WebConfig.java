package com.cai.helppsy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // "웹 URL 경로 ↔ 실제 파일 시스템 경로"를 연결해주는 매핑 도구
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
       // ResourceHandlerRegistry = 정적 리소스를 요청할 때 그 요청 URL 패턴과 실제 파일 시스템(또는 클래스패스 등) 위치를 매핑해주는 역할

        String fileUploadPath = System.getProperty("user.dir") + "/files/";

        registry.addResourceHandler("/files/**")
        // 브라우저에서 url요청이 "/files/**" 형태로 오면
                .addResourceLocations("file:" + fileUploadPath);
                // 실제 운영체제의 파일 경로(여기서는 ${user.dir}/files/)에서 그 파일을 찾아서 응답
    }
}
