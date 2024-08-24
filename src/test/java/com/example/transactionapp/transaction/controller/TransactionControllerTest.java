package com.example.transactionapp.transaction.controller;

import com.example.transactionapp.transaction.dto.PageResponseDto;
import com.example.transactionapp.transaction.entity.Transaction;
import com.example.transactionapp.transaction.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
@ExtendWith(MockitoExtension.class)
@WithMockUser
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    private Transaction transaction;

    @BeforeEach
    void setUp() {
        transaction = new Transaction();
        transaction.setTrxId(1L);
        transaction.setCustomerId("123");
        transaction.setAccountNumber("456");
        transaction.setDescription("Fund Transfer");
    }

    @Test
    void getTransactions_ShouldReturnPageOfTransactions() throws Exception {
        Page<Transaction> transactions = new PageImpl<>(List.of(transaction));
        when(transactionService.searchTransactions(anyString(), anyString(), anyString(), any(PageRequest.class)))
                .thenReturn(transactions);

        mockMvc.perform(get("/api/transactions")
                        .param("customer-id", "123")
                        .param("account-number", "456")
                        .param("description", "Fund"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(new PageResponseDto<>(0,1,1, List.of(transaction)))));

        verify(transactionService, times(1)).searchTransactions(anyString(), anyString(), anyString(), any(PageRequest.class));
    }

    @Test
    void getTransactions_ShouldReturnEmptyPage_WhenNoTransactionsFound() throws Exception {
        when(transactionService.searchTransactions(anyString(), anyString(), anyString(), any(PageRequest.class)))
                .thenReturn(Page.empty());

        mockMvc.perform(get("/api/transactions")
                        .param("customer-id", "unknown")
                        .param("account-number", "unknown")
                        .param("description", "unknown"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{}"));

        verify(transactionService, times(1)).searchTransactions(anyString(), anyString(), anyString(), any(PageRequest.class));
    }

    private static String asJsonString(final Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);

    }
}
