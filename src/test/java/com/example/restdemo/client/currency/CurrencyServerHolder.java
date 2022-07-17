package com.example.restdemo.client.currency;

import com.example.restdemo.client.currency.model.CurrencyResponse;
import java.util.Map;

public class CurrencyServerHolder {

  public CurrencyResponse currencyResponse = new CurrencyResponse(
      Map.of("RUB", 54.199902)
  );
}
