package com.example.order.controller;

import com.example.order.dto.ProductCreateRequest;
import com.example.order.usecase.ProductUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 商品接口测试
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("商品接口测试")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductUseCase productUseCase;

    @Test
    @DisplayName("测试创建商品成功")
    void testCreateProductSuccess() throws Exception {
        ProductCreateRequest request = new ProductCreateRequest();
        request.setName("测试商品");
        request.setPrice(new java.math.BigDecimal("99.99"));
        request.setDescription("商品描述");

        when(productUseCase.createProduct(any(), any(), any())).thenReturn(1L);

        ObjectMapper objectMapper = new ObjectMapper();
        
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").value(1));
    }

    @Test
    @DisplayName("测试查询商品列表")
    void testListProducts() throws Exception {
        when(productUseCase.findAllOnShelf()).thenReturn(java.util.Collections.emptyList());

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray());
    }
}
