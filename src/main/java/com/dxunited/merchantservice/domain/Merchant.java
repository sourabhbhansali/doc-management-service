package com.dxunited.merchantservice.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Merchant {
    private static final String STATUS = "status";


    public String buildMerchant(Map<String, String> inputMap) {
        Map<String, String> merchantUpdateMap = new HashMap<>();
        merchantUpdateMap.put("id", inputMap.get("merchantId"));
        merchantUpdateMap.put(STATUS,inputMap.get(STATUS));
        merchantUpdateMap.put("siteId", inputMap.get("siteId"));
        merchantUpdateMap.put("tenantId", inputMap.get("tenantId"));
        merchantUpdateMap.put("name", inputMap.get("merchantName"));
        merchantUpdateMap.put("merchantRank", inputMap.get("merchantRank"));
        merchantUpdateMap.put("description", inputMap.get("merchantDescription"));
        merchantUpdateMap.put("provider", inputMap.get("provider"));
        merchantUpdateMap.put("updatedAt", inputMap.get("modifiedDate"));
        merchantUpdateMap.put("modifiedBy", inputMap.get("modifiedBy"));
        merchantUpdateMap.put("createdAt", inputMap.get("createdDate"));
        merchantUpdateMap.put("category", inputMap.get("category"));
        Gson gson = new Gson();
        return gson.toJson(merchantUpdateMap);
    }

    @SneakyThrows
    public Map<String, String> convertStringToMap(String value) {
        return new ObjectMapper().readValue(value, HashMap.class);
    }
}
