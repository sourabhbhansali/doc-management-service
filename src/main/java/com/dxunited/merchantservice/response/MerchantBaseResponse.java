package com.dxunited.merchantservice.response;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MerchantBaseResponse {
    public GenericResponse buildResponse(String successMessage,  String failureMessage,
                                          Object response) {
        GenericResponse httpResponse;
        if (Objects.nonNull(response))
            httpResponse = new GenericResponse(successMessage, response);
        else
            httpResponse = new GenericResponse(failureMessage, null);
        return httpResponse;
    }

}

