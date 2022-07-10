package com.doc.management.service;

import com.doc.management.model.FileInfo;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class DocManagementServiceTest {

    @Mock
    DocManagementService docManagementService;

    @Test
    public void shouldGetAllFiles() throws Exception {
        List<FileInfo> files = docManagementService.getAllFiles();
        Assertions.assertThat(files).isEmpty();
    }



}
