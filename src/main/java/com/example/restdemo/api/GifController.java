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
@RequestMapping("/api/v1/gif")
public class GifController {

    private final ResponseService service;

    @GetMapping("{symbols}")
    public ResponseEntity<ResponseWithGif> getGif(@PathVariable String symbols, boolean compareValue) {
        return ResponseEntity.ok().body(service.getGif(symbols, compareValue));
    }
}