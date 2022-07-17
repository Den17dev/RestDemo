package com.example.restdemo.client.gif.model;

import lombok.Data;
import org.springframework.core.env.Environment;

@Data
public class GifRequest {

    private String api_key;

    private String q;
    private Integer limit;
    private Integer offset;
    private String rating;
    private String lang;

    public GifRequest(Environment environment) {
        api_key = environment.getProperty("api_key");
        limit = Integer.valueOf(environment.getProperty("limit"));
        offset = Integer.valueOf(environment.getProperty("offset"));
        rating = environment.getProperty("rating");
        lang = environment.getProperty("lang");
    }
}