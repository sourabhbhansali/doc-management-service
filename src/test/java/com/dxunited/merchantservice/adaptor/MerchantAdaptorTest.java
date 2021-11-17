package com.dxunited.merchantservice.adaptor;

import com.dxunited.merchantservice.connector.MerchantDBConnector;
import com.dxunited.merchantservice.repository.MerchantRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.HashMap;

@RunWith(MockitoJUnitRunner.class)
public class MerchantAdaptorTest {

    @Mock
    MerchantDBConnector merchantDBConnector;
    @InjectMocks
    MerchantAdaptor merchantAdaptor;
    @Mock
    ObjectMapper mapper;
    @Mock
    MerchantRepository merchantRepository;

    @Test
    public void insertMerchantTest() throws IOException {
        MongoCollection dbCollection = Mockito.mock(MongoCollection.class);
        String merchantString = "{\n" +
                "    \"externalMerchantId\": \"3\",\n" +
                "    \"merchantId\": \"3\",\n" +
                "    \"type\": \"string\",\n" +
                "}";
        Mockito.when(merchantDBConnector.getMerchantCollection()).thenReturn(dbCollection);
        String responseStr = "{ \"_id\" : \"4\", \"externalMerchantId\" : \"3\", " +
                " \"type\" : \"string\", " +
                " \"merchantId\" : \"4\"," +
                " } ";
        HashMap<String, String> merchantMap = new HashMap<>();
        merchantMap.put("merchantId", "3");
        merchantMap.put("externalMerchantId", "3");
        merchantMap.put("type", "string");
        Mockito.when(mapper.readValue(merchantString, HashMap.class)).thenReturn(merchantMap);
        Mockito.when(merchantRepository.insertMerchant(Mockito.any(), Mockito.any())).thenReturn(responseStr);
        Assert.assertEquals(merchantAdaptor.insertMerchant(merchantString).get("merchant"), responseStr);
    }
}
