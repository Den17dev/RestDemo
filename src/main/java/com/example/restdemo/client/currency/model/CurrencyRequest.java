package com.example.restdemo.client.currency.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.env.Environment;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRequest {

    private String app_id;
    private String base;

    private String symbols;
    private Boolean prettyprint;
    private Boolean show_alternative;

    private Integer oops;

    public CurrencyRequest(Environment environment) {
        this.app_id = environment.getProperty("app_id");
        this.base = environment.getProperty("base");
        this.prettyprint = Boolean.parseBoolean(environment.getProperty("prettyprint"));
        this.show_alternative = Boolean.parseBoolean(environment.getProperty("show_alternative"));
    }
}