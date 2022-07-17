package com.example.restdemo.api;

import com.example.restdemo.service.ResponseService;
import com.example.restdemo.service.model.ResponseWithGif;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/currency")
public class CurrencyController {

  private final ResponseService service;

  @GetMapping("{symbols}")
  public ResponseEntity<ResponseWithGif> getCurrency(@PathVariable String symbols) {
    return ResponseEntity.ok().body(service.getCurrency(symbols));
  }
}