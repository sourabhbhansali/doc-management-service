package com.dxunited.merchantservice.controller;

import com.dxunited.merchantservice.service.MerchantService;
import com.dxunited.merchantservice.util.TestUtil;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MerchantController.class)
@AutoConfigureMockMvc(addFilters = false)
public class MerchantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MerchantService merchantService;

    @Test
    public void shouldCreateMerchant() throws Exception {
        BDDMockito.willDoNothing().given(merchantService).createMerchantEvent(Mockito.any());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/tenants/merchants")
                        .content(TestUtil.createMerchant)
                        .headers(TestUtil.mockHeaders())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Merchant Created")));
    }
}