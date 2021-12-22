package com.dxunited.merchantservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Merchant {
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
    private String category;
    private String subCategory;
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


}
