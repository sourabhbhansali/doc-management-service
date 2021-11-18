package com.dxunited.merchantservice.builders;

import com.dxunited.merchantservice.adaptor.MerchantAdaptor;
import com.dxunited.merchantservice.response.request.CreateResponse;
import com.dxunited.merchantservice.response.request.IRequestResponse;
import com.dxunited.merchantservice.utils.MerchantUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Component
public class MerchantReferenceService {

    private String merchantString;

    public MerchantReferenceService() {
    }

    public MerchantReferenceService(String merchantString) {
        this.merchantString = merchantString;
    }

    public IRequestResponse build(String merchantId, MerchantAdaptor merchantAdaptor) {
        MerchantUtil merchantUtil = new MerchantUtil();
        String merchantReferenceData = merchantUtil.getMerchantReferenceData(merchantString);
        String merchant = merchantAdaptor.insertMerchantReference(merchantId, merchantReferenceData);
        return CreateResponse
                .builder()
                .merchantId(merchant)
                .build();
    }
}
