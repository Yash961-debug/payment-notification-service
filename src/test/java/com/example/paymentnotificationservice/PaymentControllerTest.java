package com.example.paymentnotificationservice;

import com.example.paymentnotificationservice.dto.PaymentRequest;
import com.example.paymentnotificationservice.entity.PaymentMethod;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreatePayment() throws Exception {

        PaymentRequest request = new PaymentRequest();
        request.setApplicationId("APP101");
        request.setAmount(2500.0);
        request.setPaymentMethod(PaymentMethod.CREDIT_CARD);

        mockMvc.perform(post("/api/v1/payments/notify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnBadRequestForInvalidRequest() throws Exception {

        PaymentRequest request = new PaymentRequest();

        mockMvc.perform(post("/api/v1/payments/notify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}