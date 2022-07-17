package com.example.restdemo.client.gif.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GifResponse {
    private ArrayList<LinkedHashMap<Object, Object>> data;
}