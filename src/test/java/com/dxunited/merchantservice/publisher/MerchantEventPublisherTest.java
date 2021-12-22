package com.dxunited.merchantservice.publisher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class MerchantEventPublisherTest {

    @Mock
    private MerchantEventPublisher merchantEventPublisher;

    @Test
    public void shouldSendMessage() throws Exception {
        doNothing().when(merchantEventPublisher).sendMessage(Mockito.anyString(), Mockito.anyString());
        merchantEventPublisher.sendMessage(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(merchantEventPublisher, times(1)).sendMessage(Mockito.anyString(), Mockito.anyString());
    }
}
