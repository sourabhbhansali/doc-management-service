package com.dxunited.merchantservice.controller;

import com.dxunited.merchantservice.response.CreateMerchantResponse;
import com.dxunited.merchantservice.service.MerchantService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "api/tenants/merchants")
public class MerchantController {
    @Autowired
    MerchantService merchantservice;

    @PostMapping("{tenantId}/{siteId}")
    public ResponseEntity<CreateMerchantResponse> createMerchant(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorization,
                                                                 String correlationId,
                                                                 @PathVariable("tenantId") Long tenantId,
                                                                 @PathVariable("siteId") Long siteId,
                                                                 @RequestBody String merchantString) throws JsonProcessingException {
        merchantservice.createMerchantEvent(merchantString,tenantId, siteId);
        CreateMerchantResponse createMerchantResponse = CreateMerchantResponse.builder().success(true).status(HttpStatus.CREATED.value()).message("Merchant Created")
                .build();
        return new ResponseEntity<>(createMerchantResponse, HttpStatus.OK);
    }

    @PutMapping("{tenantId}/{siteId}")
    public ResponseEntity<CreateMerchantResponse> updateMerchant(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorization,
                                                                 String correlationId,
                                                                 @PathVariable("tenantId") Long tenantId,
                                                                 @PathVariable("siteId") Long siteId,
                                                                 @RequestBody String merchantString) throws JsonProcessingException {
        merchantservice.updateMerchantEvent(merchantString,tenantId, siteId);
        CreateMerchantResponse createMerchantResponse = CreateMerchantResponse.builder().success(true).status(HttpStatus.OK.value()).message("Merchant Updated")
                .build();
        return new ResponseEntity<>(createMerchantResponse, HttpStatus.OK);
    }

    @PatchMapping("{tenantId}/{siteId}")
    public ResponseEntity<CreateMerchantResponse> merchantStatusAction(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorization,
                                                                 String correlationId,
                                                                 @PathVariable("tenantId") Long tenantId,
                                                                 @PathVariable("siteId") Long siteId,
                                                                 @RequestBody String merchantString) throws JsonProcessingException {
        merchantservice.merchantStatusAction(merchantString,tenantId, siteId);
        CreateMerchantResponse createMerchantResponse = CreateMerchantResponse.builder().success(true).status(HttpStatus.OK.value()).message("Merchant status updated")
                .build();
        return new ResponseEntity<>(createMerchantResponse, HttpStatus.OK);
    }
}