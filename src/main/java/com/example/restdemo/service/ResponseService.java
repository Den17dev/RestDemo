package com.example.restdemo.service;

import com.example.restdemo.service.model.ResponseWithGif;

public interface ResponseService {

  Object test();

  ResponseWithGif getCurrency(String symbols);

  ResponseWithGif getGif(String q, boolean compareValue);
}