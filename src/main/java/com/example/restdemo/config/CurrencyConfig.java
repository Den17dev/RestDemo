package com.example.restdemo.config;

import com.example.restdemo.client.currency.CurrencyClient;
import com.example.restdemo.client.currency.model.CurrencyRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class CurrencyConfig {

    private final String currencyAddress;
    private final CurrencyRequest currencyRequest;

    public CurrencyConfig(Environment environment) {
        this.currencyAddress = environment.getProperty("currency.server");
        this.currencyRequest = new CurrencyRequest(environment);
    }

    @Bean
    public CurrencyClient currencyClient() {
        return new CurrencyClient(currencyAddress, currencyRequest);
    }
}