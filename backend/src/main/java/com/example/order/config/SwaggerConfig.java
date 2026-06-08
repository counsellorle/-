package com.example.order.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger / OpenAPI 配置
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Configuration
public class SwaggerConfig {

    /**
     * 配置 OpenAPI 文档
     *
     * @return OpenAPI
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("企业级订单管理系统 API")
                        .version("1.0.0")
                        .description("提供订单管理、商品管理、支付等接口")
                        .contact(new Contact()
                                .name("技术团队")
                                .email("support@example.com")));
    }
}
