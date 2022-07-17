package com.example.restdemo.client.currency;

import com.example.restdemo.client.currency.model.CurrencyRequest;
import com.example.restdemo.client.currency.model.CurrencyResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.Request.Options;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CurrencyClient {

    private volatile CurrencyMethods currencyMethods;
    private final CurrencyRequest currencyRequest;
    private Map<String, Object> params = new HashMap<>(3);

    //Для чего данная опция?
    private Request.Options options = new Options(40, TimeUnit.SECONDS,
            15, TimeUnit.SECONDS,
            true
    );

    /**
     * Для чего данный конструктор?
     * public CurrencyClient(String serverAddress, String yesterday, Options options) {
     * this.yesterday = yesterday;
     * this.options = options;
     * createUpdateClient(serverAddress);
     * }
     */

    public CurrencyClient(String serverAddress, CurrencyRequest currencyRequest) {
        this.currencyRequest = currencyRequest;
        createUpdateClient(serverAddress);
    }

    private void createUpdateClient(String serverAddress) {

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);

        currencyMethods = Feign.builder()
                .client(new OkHttpClient())
                .options(options)
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder(objectMapper))
                .logger(new Slf4jLogger(CurrencyMethods.class))
                .logLevel(Logger.Level.FULL)
                .errorDecoder(new FeignErrorDecoder(new JacksonDecoder(objectMapper)))
                .target(CurrencyMethods.class, serverAddress);
    }

    //Fill params HashMap for request parameters
    private void setRequestParams(String symbols) {
        params.put("app_id", currencyRequest.getApp_id());
        params.put("base", currencyRequest.getBase());
        currencyRequest.setSymbols(symbols);
        params.put("symbols", currencyRequest.getSymbols());
        params.put("oops", currencyRequest.getOops());
        params.put("prettyprint", currencyRequest.getPrettyprint());
        params.put("show_alternative", currencyRequest.getShow_alternative());
    }

    //Get currency current value
    public CurrencyResponse getCurrencyJson(String symbols) {
        setRequestParams(symbols);
        return currencyMethods.getCurrency(params);
    }

    //Get currency value from yesterday
    public CurrencyResponse getCurrencyJson(String symbols, String yesterday) {
        setRequestParams(symbols);
        return currencyMethods.getCurrency(params, yesterday);
    }
}