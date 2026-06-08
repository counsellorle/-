package com.example.order.controller;

import com.example.order.usecase.CreateOrderUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 订单接口测试
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("订单接口测试")
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateOrderUseCase createOrderUseCase;

    @Test
    @DisplayName("测试创建订单成功")
    void testCreateOrderSuccess() throws Exception {
        // TODO: 待实现后添加测试
        // CreateOrderRequest request = new CreateOrderRequest();
        // var item1 = new CreateOrderRequest.OrderItemDTO();
        // item1.setProductId(1L);
        // item1.setQuantity(2);
        // request.setItems(Arrays.asList(item1));
        // request.setReceiverAddress("北京市朝阳区 xxx 路 xxx 号");
        // request.setRemark("请尽快发货");
        // request.setPaymentMethod(2);
        //
        // when(createOrderUseCase.createOrder(any(), any(), any(), any(), any())).thenReturn("ORD202606080001");
        //
        // ObjectMapper objectMapper = new ObjectMapper();
        // 
        // mockMvc.perform(post("/orders")
        //                 .contentType(MediaType.APPLICATION_JSON)
        //                 .content(objectMapper.writeValueAsString(request)))
        //         .andExpect(status().isOk())
        //         .andExpect(jsonPath("$.code").value(200))
        //         .andExpect(jsonPath("$.data.orderNo").value("ORD202606080001"));
    }

    @Test
    @DisplayName("测试查询订单详情")
    void testGetOrderDetail() throws Exception {
        // TODO: 待实现后添加测试
        // mockMvc.perform(get("/orders/ORD202606080001"))
        //         .andExpect(status().isOk());
    }
}
