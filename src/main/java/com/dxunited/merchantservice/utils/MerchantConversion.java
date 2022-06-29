package com.dxunited.merchantservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    public String convertObjectToJsonString(Object object) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public <T> T convertJsonStrToObj(String jsonStr, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonStr, clazz);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @SneakyThrows
    public Map<String, Object> convertStringToMap(String value) {
        return new ObjectMapper().readValue(value, HashMap.class);
    }

    public String buildMerchantForClientProduct(Map<String, Object> inputMap) {
        Map<String, Object> merchantUpdateMap = new HashMap<>();
        merchantUpdateMap.put("id", inputMap.get("merchantId"));
        merchantUpdateMap.put("status",inputMap.get("status"));
        merchantUpdateMap.put("siteId", inputMap.get("siteId"));
        merchantUpdateMap.put("clientId", inputMap.get("clientId"));
        merchantUpdateMap.put("name", inputMap.get("merchantName"));
        Double merchantRank = (Double)inputMap.get("merchantRank");
        merchantUpdateMap.put("merchantRank", merchantRank.intValue());
        merchantUpdateMap.put("description", inputMap.get("merchantDescription"));
        merchantUpdateMap.put("provider", inputMap.get("provider"));
        merchantUpdateMap.put("updatedAt", inputMap.get("modifiedDate"));
        merchantUpdateMap.put("modifiedBy", inputMap.get("modifiedBy"));
        merchantUpdateMap.put("createdAt", inputMap.get("createdDate"));
        merchantUpdateMap.put("categories", inputMap.get("categories"));
        Gson gson = new Gson();
        return gson.toJson(merchantUpdateMap);
    }
}
