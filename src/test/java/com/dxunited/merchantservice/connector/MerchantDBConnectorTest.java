package com.dxunited.merchantservice.connector;

import com.dxunited.merchantservice.config.MerchantDBConfig;
import com.dxunited.merchantservice.repository.MerchantRepository;
import com.dxunited.merchantservice.util.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.assertj.core.api.Assertions;
import org.bson.Document;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MerchantDBConnectorTest {
    @InjectMocks
            @Spy
    MerchantDBConnector merchantDBConnector;
    @Mock
    MerchantDBConnector mockMongoConnector;
    @Mock
    private MongoCollection<Document> mockCollection;
    @Mock
    MerchantRepository merchantRepository;
    @Mock
    Document document;
    @Mock
    MongoDatabase mongoDatabase;
    @Mock
    MerchantDBConfig merchantDBConfig;

    @Ignore
    public void shouldSaveMerchant() throws IOException {
        doNothing().when(merchantRepository).insertMerchant(mockCollection, document);
        merchantRepository.insertMerchant(mockCollection, document);
        when(merchantDBConfig.getHost()).thenReturn("localhost");
        when(merchantDBConfig.getPort()).thenReturn(27017);
        when(merchantDBConfig.getDatabase()).thenReturn("merchant");
        when(merchantDBConfig.getMerchantCollection()).thenReturn("merchant");
        when(merchantDBConnector.getMerchantCollectionFromDb()).thenReturn(mockCollection);
        when(merchantDBConnector.getMerchantCollection()).thenReturn(mockCollection);
        merchantDBConnector.saveMerchant("{\"name\":\"sonoo\",\"salary\":600000.0,\"age\":27}");
        verify(merchantRepository, times(1)).insertMerchant(mockCollection, document);
    }

}
