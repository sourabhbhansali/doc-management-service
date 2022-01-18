package com.dxunited.merchantservice.util;

import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.Map;

public class TestUtil {

    public static HttpHeaders mockHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer ");
        httpHeaders.setAll(map);
        return httpHeaders;
    }

}
