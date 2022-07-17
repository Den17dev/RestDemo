package com.example.restdemo.client.gif;

import com.example.restdemo.client.gif.model.GifRequest;
import com.example.restdemo.client.gif.model.GifResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GifClient {

    private volatile GifMethods gifMethods;

    private final GifRequest gifRequest;
    private Map<String, Object> params = new HashMap<>(3);

    private Request.Options options = new Request.Options(40, TimeUnit.SECONDS,
            15, TimeUnit.SECONDS,
            true
    );

    public GifClient(String serverAddress, GifRequest gifRequest) {
        this.gifRequest = gifRequest;
        createUpdateClient(serverAddress);
    }

    /**
     * Для чего данный конструктор?
     * public CurrencyClient(String serverAddress, String yesterday, Options options) {
     * this.yesterday = yesterday;
     * this.options = options;
     * createUpdateClient(serverAddress);
     * }
     */

    private void createUpdateClient(String serverAddress) {

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);

        gifMethods = Feign.builder()
                .client(new OkHttpClient())
                .options(options)
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder(objectMapper))
                .logger(new Slf4jLogger(GifMethods.class))
                .logLevel(Logger.Level.FULL)
                .errorDecoder(new FeignGifErrorDecoder(new JacksonDecoder(objectMapper)))
                .target(GifMethods.class, serverAddress);
    }

    //Fill params HashMap for request parameters
    private void setParams(boolean compareResult) {
        params.put("api_key", gifRequest.getApi_key());
        gifRequest.setQ(compareResult ? "rich" : "broke");
        params.put("q", gifRequest.getQ());
        params.put("limit", gifRequest.getLimit());
        params.put("offset", gifRequest.getOffset());
        params.put("rating", gifRequest.getRating());
        params.put("lang", gifRequest.getLang());
    }

    //Get currency current value
    public GifResponse getGif(String q, boolean compareValue) {
        setParams(compareValue);
        return gifMethods.getGifResponse(params, q);
    }
}