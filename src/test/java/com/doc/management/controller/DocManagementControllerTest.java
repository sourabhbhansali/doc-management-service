package com.doc.management.controller;

import com.doc.management.model.FileInfo;
import com.doc.management.service.DocManagementService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(value = DocManagementController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DocManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocManagementService docManagementService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void shouldUploadFile() throws Exception {
        BDDMockito.willDoNothing().given(docManagementService).save(Mockito.any());
        MockMultipartFile file = new MockMultipartFile(
                "file", "filename.txt",
                "multipart/form-data", "Hello".getBytes());
        MockMvc mockMvc1
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc1.perform(MockMvcRequestBuilders
                        .multipart("/api/files/upload")
                        .file(file))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Matchers.is("File Uploaded Successfully !!!")));
    }

    @Test
    public void shouldGetAllFiles() throws Exception {
        Mockito.when(docManagementService.getAllFiles()).thenReturn(mockFileInfo());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/files")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fileName",
                        Matchers.is("testFile")));
    }

    private List<FileInfo> mockFileInfo() {
        List<FileInfo> fileInfos = new ArrayList<>();
        fileInfos.add(FileInfo.builder().fileName("testFile").build());
        return fileInfos;
    }
}