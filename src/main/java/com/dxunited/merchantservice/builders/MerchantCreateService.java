package com.dxunited.merchantservice.builders;

import com.dxunited.merchantservice.adaptor.MerchantAdaptor;
import com.dxunited.merchantservice.response.request.CreateResponse;
import com.dxunited.merchantservice.response.request.IRequestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class MerchantCreateService implements MerchantService {

    private String merchantString;

    public MerchantCreateService() {}

    public MerchantCreateService(String merchantString) {
        this.merchantString = merchantString;
    }

    @Override
    public IRequestResponse build(MerchantAdaptor merchantAdaptor) {
        Map<String, String> merchantDetails = merchantAdaptor.insertMerchant(merchantString);
        MerchantReferenceService referenceBuilder = new MerchantReferenceService(merchantString);
        referenceBuilder.build(merchantDetails.get("id"), merchantAdaptor);
        return CreateResponse
                .builder()
                .merchantId(merchantDetails.get("merchant"))
                .build();
    }
}
