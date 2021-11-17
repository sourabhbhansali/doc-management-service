package com.dxunited.merchantservice.controller;

import com.dxunited.merchantservice.enums.Event;
import com.dxunited.merchantservice.response.EventResponse;
import com.dxunited.merchantservice.response.GenericResponse;
import com.dxunited.merchantservice.response.MerchantBaseResponse;
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
@RequestMapping(path = "/api/tenant")
public class MerchantController {
    @Autowired
    MerchantService merchantservice;
    @Autowired
    MerchantBaseResponse merchantBaseResponse;

    @ApiOperation(value = "Create Merchant")
    @PostMapping("/merchants")
    @ApiImplicitParam(name = HttpHeaders.AUTHORIZATION, value = "Authorization", required = true, paramType = "header", dataTypeClass = String.class)
    public ResponseEntity<GenericResponse<EventResponse>> createMerchant(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorization,
                                                         String correlationId,
                                                         @RequestBody String merchantString) throws JsonProcessingException {
        EventResponse eventResponse = merchantservice.createMerchantEvent(Event.CREATE.getTopic(), merchantString);
        GenericResponse genericResponse = GenericResponse.builder()
                .data(eventResponse).message("Merchant Created").build();
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }
}