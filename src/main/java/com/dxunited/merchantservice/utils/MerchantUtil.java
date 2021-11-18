package com.dxunited.merchantservice.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MerchantUtil {
    public String getMerchantReferenceData(String merchant) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = new JsonObject();
        JsonObject obj = (JsonObject) jsonParser.parse(merchant);
        String merchantId = obj.get("merchantId").getAsString();
        String externalMerchantId = obj.get("externalMerchantId").getAsString();
        String merchantUrl = obj.get("merchantUrl").getAsString();
        String supplierId = obj.get("supplierId").getAsString();
        jsonObject.addProperty("merchantId", merchantId);
        jsonObject.addProperty("externalMerchantId", externalMerchantId);
        jsonObject.addProperty("merchantUrl", merchantUrl);
        jsonObject.addProperty("supplierId", supplierId);
        return jsonObject.toString();
    }
}
