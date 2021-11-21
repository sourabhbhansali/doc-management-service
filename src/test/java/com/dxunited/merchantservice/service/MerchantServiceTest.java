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
    @InjectMocks
    MerchantService merchantService;

    @Mock
    private MerchantRepository merchantRepository ;

    @Mock
    private MerchantEventPublisher merchantEventPublisher;
    @Mock
    MerchantDBConnector merchantDBConnector;

    @Test
    public void shouldCreateMerchant() throws Exception {
        doNothing().when(merchantEventPublisher).sendMessage(Mockito.anyString(), Mockito.anyString());
        merchantEventPublisher.sendMessage(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(merchantEventPublisher, times(1)).sendMessage(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void shouldSaveMerchant() throws Exception {
        doNothing().when(merchantDBConnector).saveMerchant(Mockito.anyString());
        merchantDBConnector.saveMerchant(Mockito.anyString());
        Mockito.verify(merchantDBConnector, times(1)).saveMerchant(Mockito.anyString());
    }

}
