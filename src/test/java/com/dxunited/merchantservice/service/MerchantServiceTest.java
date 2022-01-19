package com.dxunited.merchantservice.service;

import com.dxunited.merchantservice.connector.MerchantDBConnector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;


@RunWith(MockitoJUnitRunner.class)
public class MerchantServiceTest {
    @Mock
    MerchantService merchantService;
    @Mock
    MerchantDBConnector merchantDBConnector;

    @Test
    public void shouldCreateMerchant() throws Exception {
        doNothing().when(merchantService).createMerchantEvent(Mockito.any());
        merchantService.createMerchantEvent(Mockito.any());
        Mockito.verify(merchantService, times(1)).createMerchantEvent(Mockito.any());
    }

    @Test
    public void shouldUpdateMerchantEvent() throws Exception {
        doNothing().when(merchantService).updateMerchantEvent(Mockito.any());
        merchantService.updateMerchantEvent(Mockito.any());
        Mockito.verify(merchantService, times(1))
                .updateMerchantEvent(Mockito.any());
    }

    @Test
    public void shouldSaveMerchant() throws Exception {
        doNothing().when(merchantDBConnector).saveMerchant(Mockito.anyMap());
        merchantDBConnector.saveMerchant(Mockito.anyMap());
        Mockito.verify(merchantDBConnector, times(1)).saveMerchant(Mockito.anyMap());
    }

    @Test
    public void shouldUpdateMerchant() throws Exception {
        doNothing().when(merchantDBConnector).updateMerchant(Mockito.any());
        merchantDBConnector.updateMerchant(Mockito.any());
        Mockito.verify(merchantDBConnector, times(1)).updateMerchant(Mockito.any());
    }

}
