package com.doc.management.controller;

import com.doc.management.service.DocManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/merchants")
public class MerchantController {

    @Autowired
    DocManagementService merchantservice;

    /*@PostMapping
    public ResponseEntity<CreateMerchantResponse> createMerchant(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorization,
            String correlationId,
            @RequestBody CreateDocRequest createDocRequest) throws Exception {
        merchantservice.createMerchantEvent(createDocRequest);
        CreateMerchantResponse createMerchantResponse = CreateMerchantResponse.builder()
                .success(true).status(HttpStatus.CREATED.value()).message("Merchant Creation Request Received")
                .build();
        return new ResponseEntity<>(createMerchantResponse, HttpStatus.OK);
    }*/
}