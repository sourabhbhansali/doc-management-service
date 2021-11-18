package com.dxunited.merchantservice.builders;

import com.dxunited.merchantservice.adaptor.MerchantAdaptor;
import com.dxunited.merchantservice.response.request.IRequestResponse;
import org.springframework.stereotype.Service;

@Service
public interface MerchantService {
    IRequestResponse build(MerchantAdaptor merchantAdaptor);
}
