package com.dxunited.merchantservice.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public static boolean isReviewApproved(String status) {
        if (StringUtils.isNotEmpty(status) && ("Approve".equalsIgnoreCase(status) || "Enabled".equalsIgnoreCase(status)))
            return true;
        return false;
    }

    public static String getCurrentDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedDate = formatter.format(localDateTime);
            return formattedDate;
        } catch (Exception e) {
            throw e;
        }
    }
}
