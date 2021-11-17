package com.dxunited.merchantservice.service;

import com.dxunited.merchantservice.adaptor.EventAdaptor;
import com.dxunited.merchantservice.constants.Constants;
import com.dxunited.merchantservice.response.EventResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MerchantServiceTest {
    @InjectMocks
    MerchantService merchantService;
    @Mock
    EventAdaptor eventAdaptor;

    @Test
    public void createMerchantService()
    {
        EventResponse response = new EventResponse();
        Mockito.when(eventAdaptor.sendMessage("CREATE_MERCHANT","key:\"STRING\""))
                .thenReturn(response);
        Assert.assertEquals(merchantService.createMerchantEvent(Constants.CREATE_MERCHANT,"key:\"STRING\""),
                response);
    }
}
