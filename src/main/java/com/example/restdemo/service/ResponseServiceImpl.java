package com.example.restdemo.service;

import com.example.restdemo.client.currency.CurrencyClient;
import com.example.restdemo.client.currency.model.CurrencyResponse;
import com.example.restdemo.client.gif.GifClient;
import com.example.restdemo.client.gif.model.GifResponse;
import com.example.restdemo.service.model.ResponseWithGif;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ResponseServiceImpl implements ResponseService {

  private final CurrencyClient currencyClient;
  private final GifClient gifClient;

  @Override
  public Object test() {
    return "test";
  }

  private LocalDate today = LocalDate.now();
  private LocalDate yesterday = today.minusDays(1);

  @Override
  public ResponseWithGif getCurrency(String symbols) {
    CurrencyResponse todayValue = currencyClient.getCurrencyJson(symbols);
    CurrencyResponse yesterdayValue = currencyClient.getCurrencyJson(symbols, LocalDate.now().minusDays(1).toString());
    ResponseWithGif responseWithGif = new ResponseWithGif();
    responseWithGif.setTodayValue(Double.valueOf(String.valueOf(todayValue.getRates().get(symbols))));
    responseWithGif.setYesterdayValue(Double.valueOf(String.valueOf(yesterdayValue.getRates().get(symbols))));
    return responseWithGif;
  }

  @Override
  public ResponseWithGif getGif(String q, boolean compareValue) {
    ResponseWithGif responseWithGif = getCurrency(q);
    GifResponse gifJson = gifClient.getGif(q, responseWithGif.getTodayValue() > responseWithGif.getYesterdayValue());
    Random random = new Random();
    int position = random.nextInt(25);
    responseWithGif.setUrl(gifJson.getData().get(position).get("url").toString());
    return responseWithGif;
  }
}