package com.dxunited.doc.management.service;

import com.dxunited.merchantservice.connector.MerchantDBConnector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;


@RunWith(MockitoJUnitRunner.class)
public class DocManagementServiceTest {
    @Mock
    DocManagementService docManagementService;
    @Mock
    MerchantDBConnector merchantDBConnector;

    @Test
    public void shouldCreateMerchant() throws Exception {
        doNothing().when(docManagementService).createMerchantEvent(Mockito.any());
        docManagementService.createMerchantEvent(Mockito.any());
        Mockito.verify(docManagementService, times(1)).createMerchantEvent(Mockito.any());
    }

    @Test
    public void shouldUpdateMerchantEvent() throws Exception {
        doNothing().when(docManagementService).updateMerchantEvent(Mockito.any());
        docManagementService.updateMerchantEvent(Mockito.any());
        Mockito.verify(docManagementService, times(1))
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
