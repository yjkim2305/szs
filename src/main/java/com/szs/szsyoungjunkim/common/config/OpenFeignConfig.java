package com.szs.szsyoungjunkim.common.config;

import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class OpenFeignConfig {

    @Value("${scrap.x-api-key}")
    private String apiKey;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("x-api-key", apiKey);
            requestTemplate.header("Content-Type", "application/json");
        };
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(
                1000,  //최초 재시도 대기 시간 1초
                TimeUnit.SECONDS.toMillis(5), //최대 대기 시간 5초
                3  //최대 재시도 횟수 3번
        );
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new OpenFeignErrorDecoder();
    }

}
