package com.example.restdemo.service.model;

import lombok.Data;

@Data
public class ResponseWithGif {
  private Double todayValue;
  private Double yesterdayValue;
  private String url;
}
