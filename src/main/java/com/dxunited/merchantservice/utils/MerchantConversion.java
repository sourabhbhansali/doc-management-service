package com.dxunited.merchantservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Component
public class MerchantConversion<T> {
    @Autowired
    private Gson gson;
    @Autowired
    private ObjectMapper mapper;

    @SneakyThrows
    public T convertStringToClass(String string, Class<T> clazz) {
        return gson.fromJson(string, clazz);
    }

    @SneakyThrows
    public T convertStringToMap(String string, Class<T> clazz) {
        return mapper.readValue(string, clazz);
    }
}
