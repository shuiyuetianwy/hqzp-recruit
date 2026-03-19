package com.hqzp.recruit.ai.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class DeepSeekConfig {

    @Bean
    public OkHttpClient deepSeekHttpClient(DeepSeekProperties props) {
        return new OkHttpClient.Builder()
                .connectTimeout(props.getTimeoutSeconds(), TimeUnit.SECONDS)
                .readTimeout(props.getTimeoutSeconds(), TimeUnit.SECONDS)
                .writeTimeout(props.getTimeoutSeconds(), TimeUnit.SECONDS)
                .build();
    }
}
