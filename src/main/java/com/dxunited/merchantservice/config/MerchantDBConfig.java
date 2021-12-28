package com.dxunited.merchantservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Getter
@Setter
@Configuration
public class MerchantDBConfig {
    @Value("${mongo.host}")
    private String host;
    @Value("${mongo.username}")
    private String username;
    @Value("${mongo.password}")
    private String password;
    @Value("${mongo.port}")
    private int port;
    @Value("${mongo.database}")
    private String database;
    @Value("${mongo.merchant_collection}")
    private String merchantCollection;
    @Value("${mongo.merchant_workflow_collection}")
    private String merchantWorkflowCollection;
}

