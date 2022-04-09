package com.dxunited.merchantservice.controller;

import com.dxunited.merchantservice.model.MerchantRequest;
import com.dxunited.merchantservice.response.CreateMerchantResponse;
import com.dxunited.merchantservice.service.MerchantService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/api/merchants")
public class MerchantController {

    @Autowired
    MerchantService merchantservice;

    @PostMapping
    public ResponseEntity<CreateMerchantResponse> createMerchant(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorization,
            String correlationId,
            @RequestBody MerchantRequest merchantRequest) throws Exception {
        merchantservice.createMerchantEvent(merchantRequest);
        CreateMerchantResponse createMerchantResponse = CreateMerchantResponse.builder()
                .success(true).status(HttpStatus.CREATED.value()).message("Merchant Creation Request Received")
                .build();
        return new ResponseEntity<>(createMerchantResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "Create Customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customer Created"),
            @ApiResponse(code = 401, message = "You are not authorized"),
            @ApiResponse(code = 404, message = "Resource not found")
    })

    @ApiImplicitParam(name = HttpHeaders.AUTHORIZATION, value = "Authorization",
            required = true, paramType = "header", dataTypeClass = String.class)
    @PutMapping
    public ResponseEntity<CreateMerchantResponse> updateMerchant(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorization,
            String correlationId,
            @RequestBody MerchantRequest merchantRequest) throws Exception {
        merchantservice.updateMerchantEvent(merchantRequest);
        CreateMerchantResponse createMerchantResponse = CreateMerchantResponse.builder()
                .success(true).status(HttpStatus.OK.value()).message("Merchant Updation Request Received")
                .build();
        return new ResponseEntity<>(createMerchantResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "Update Merchant Status")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Merchant Status Updated"),
            @ApiResponse(code = 401, message = "You are not authorized"),
            @ApiResponse(code = 404, message = "Resource not found")
    })

    @ApiImplicitParam(name = HttpHeaders.AUTHORIZATION, value = "Authorization",
            required = true, paramType = "header", dataTypeClass = String.class)
    @PatchMapping
    public ResponseEntity<CreateMerchantResponse> merchantStatusAction(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorization,
            String correlationId,
            @RequestBody MerchantRequest merchantRequest) throws Exception {
        merchantservice.merchantStatusAction(merchantRequest);
        CreateMerchantResponse createMerchantResponse = CreateMerchantResponse.builder()
                .success(true).status(HttpStatus.OK.value()).message("Merchant Status Updation Request Received")
                .build();
        return new ResponseEntity<>(createMerchantResponse, HttpStatus.OK);
    }
}