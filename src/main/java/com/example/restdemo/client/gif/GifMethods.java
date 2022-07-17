package com.example.restdemo.client.gif;

import com.example.restdemo.client.gif.model.GifResponse;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

import java.util.Map;

public interface GifMethods {

    @RequestLine("GET /v1/gifs/search?{q}")
    GifResponse getGifResponse(@QueryMap Map<String, Object> params, @Param("q") String q);
}