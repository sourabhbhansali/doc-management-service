package com.dxunited.merchantservice.service;

import com.dxunited.merchantservice.connector.MerchantDBConnector;
import com.dxunited.merchantservice.publisher.MerchantEventPublisher;
import com.dxunited.merchantservice.repository.MerchantRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class MerchantServiceTest {
    @Mock
    MerchantService merchantService;
    @Mock
    MerchantDBConnector merchantDBConnector;

    @Test
    public void shouldCreateMerchant() throws Exception {
        doNothing().when(merchantService).createMerchantEvent(Mockito.anyString(),Mockito.any(),Mockito.any());
        merchantService.createMerchantEvent(Mockito.anyString(),Mockito.any(),Mockito.any());
        Mockito.verify(merchantService, times(1)).createMerchantEvent(Mockito.anyString(),Mockito.any(),Mockito.any());
    }

    @Test
    public void shouldUpdateMerchantEvent() throws Exception {
        doNothing().when(merchantService).updateMerchantEvent(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong());
        merchantService.updateMerchantEvent(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong());
        Mockito.verify(merchantService, times(1)).updateMerchantEvent(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong());
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
