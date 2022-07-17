package com.example.restdemo.config;

import com.example.restdemo.client.gif.GifClient;
import com.example.restdemo.client.gif.model.GifRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class GifConfig {

    private final String gifAddress;
    private final GifRequest gifRequest;

    public GifConfig(Environment environment) {
        gifAddress = environment.getProperty("gif.server");
        gifRequest = new GifRequest(environment);
    }

    @Bean
    public GifClient gifClient() {
        return new GifClient(gifAddress, gifRequest);
    }
}
