package com.doc.management.controller;

import com.doc.management.service.DocManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

//@RunWith(SpringRunner.class)
//@WebMvcTest(value = MerchantController.class)
//@AutoConfigureMockMvc(addFilters = false)
public class MerchantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocManagementService docManagementService;

   /* @Test
    public void shouldCreateMerchant() throws Exception {
        BDDMockito.willDoNothing().given(docManagementService).createMerchantEvent(Mockito.any());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/merchants", 2, 1)
                        .content(asJsonString(mockMerchantRequest()))
                        .headers(TestUtil.mockHeaders())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Matchers.is("Merchant Creation Request Received")));
    }

    @Test
    public void shouldUpdateMerchant() throws Exception {
        BDDMockito.willDoNothing().given(docManagementService).createMerchantEvent(Mockito.any());
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/merchants", 2, 1)
                        .content(asJsonString(mockMerchantRequest()))
                        .headers(TestUtil.mockHeaders())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Matchers.is("Merchant Updation Request Received")));
    }

    @Test
    public void shouldUpdateMerchantStatus() throws Exception {
        BDDMockito.willDoNothing().given(docManagementService).createMerchantEvent(Mockito.any());
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/merchants", 2, 1)
                        .content(asJsonString(mockMerchantRequest()))
                        .headers(TestUtil.mockHeaders())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Matchers.is("Merchant Status Updation Request Received")));
    }

    private CreateDocRequest mockMerchantRequest() {
        return CreateDocRequest.builder().merchantId("1").merchantName("adidas").build();
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/
}