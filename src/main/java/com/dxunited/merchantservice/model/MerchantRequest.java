package com.dxunited.merchantservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantRequest {
    private String merchantId;
    private String externalMerchantId;
    private String merchantName;
    private String merchantLogo1;
    private String merchantLogo2;
    private String merchantImage1;
    private String merchantImage2;
    private String merchantDescription;
    private String merchantUrl;
    private String merchantGoogleMapUrl;
    private String supplierId;
    private String supplierName;
    private String provider;
    private String externalMerchantCategory;
    private List<String> categories;
    private String status;
    private String createdDate;
    private String modifiedDate;
    private String createdBy;
    private String modifiedBy;
    private String externalMaxRebate;
    private String minRebate;
    private String customerTouchPoint;
    private String customerRebatePercentage;
    private String customerMaxRebate;
    private String tenantId;
    private String siteId;
    private Map<String, Object> metadata;
}
