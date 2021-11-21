package com.dxunited.merchantservice.controller;

import com.dxunited.merchantservice.response.CreateMerchantResponse;
import com.dxunited.merchantservice.service.MerchantService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "Create Merchant")
    @PostMapping
    @ApiImplicitParam(name = HttpHeaders.AUTHORIZATION, value = "Authorization", required = true, paramType = "header", dataTypeClass = String.class)
    public ResponseEntity<CreateMerchantResponse> createMerchant(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorization,
                                                                 String correlationId,
                                                                 @RequestBody String merchantString) throws JsonProcessingException {
        merchantservice.createMerchantEvent(merchantString);
        CreateMerchantResponse createMerchantResponse = CreateMerchantResponse.builder().success(true).status(HttpStatus.CREATED.value()).message("Merchant Created")
                .build();
        return new ResponseEntity<>(createMerchantResponse, HttpStatus.OK);
    }
}