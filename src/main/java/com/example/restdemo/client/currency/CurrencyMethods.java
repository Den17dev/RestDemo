package com.example.restdemo.client.currency;

import com.example.restdemo.client.currency.model.CurrencyResponse;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;
import java.util.Map;

public interface CurrencyMethods {

  @RequestLine("GET /api/latest.json")
  CurrencyResponse getCurrency(@QueryMap Map<String, Object> params);

  @RequestLine("GET /api/historical/{yesterday}.json")
  CurrencyResponse getCurrency(@QueryMap Map<String, Object> params, @Param("yesterday") String yesterday);
}
