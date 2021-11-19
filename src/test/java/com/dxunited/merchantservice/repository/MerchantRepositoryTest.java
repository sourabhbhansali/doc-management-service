package com.dxunited.merchantservice.repository;

import com.dxunited.merchantservice.repository.MerchantRepository;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class MerchantRepositoryTest {
    @Mock
    MerchantRepository merchantRepository;
    @Mock
    Document document;
    @Mock
    private MongoCollection mockCollection;

    @Test
    public void shouldInsertMerchant() throws Exception {
        doNothing().when(merchantRepository).insertMerchant(mockCollection, document);
        merchantRepository.insertMerchant(mockCollection, document);
        Mockito.verify(merchantRepository, times(1)).insertMerchant(mockCollection, document);
    }

}
