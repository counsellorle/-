package com.example.order.controller;

import com.example.order.dto.PayCallbackRequest;
import com.example.order.usecase.PaymentUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 支付回调 Controller
 * 负责处理支付宝、微信等支付平台的回调通知
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@RestController
@RequestMapping("/pay")
@Slf4j
public class PayCallbackController {

    @Autowired
    private PaymentUseCase paymentUseCase;

    /**
     * 支付宝支付回调接口
     *
     * @param params 回调参数
     * @return 响应结果
     */
    @PostMapping("/alipay/callback")
    public String alipayCallback(@RequestParam(required = false) Map<String, String> params) {
        log.info("收到支付宝回调，params: {}", params);

        if (params == null || params.isEmpty()) {
            log.warn("支付宝回调参数为空");
            return "failure";
        }

        try {
            String orderNo = params.get("out_trade_no");
            String tradeNo = params.get("trade_no");
            String tradeStatus = params.get("trade_status");

            // 验证签名（实际应调用支付宝验签接口）
            boolean signVerified = verifyAlipaySign(params);
            if (!signVerified) {
                log.error("支付宝签名验证失败");
                return "failure";
            }

            // 处理支付成功
            if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {
                paymentUseCase.handlePaymentSuccess(orderNo, tradeNo, 2); // 2-支付宝
                return "success";
            }

            log.info("支付宝回调处理完成，orderNo: {}, status: {}", orderNo, tradeStatus);
            return "success";

        } catch (Exception e) {
            log.error("支付宝回调处理失败", e);
            return "failure";
        }
    }

    /**
     * 微信支付回调接口
     *
     * @param params 回调参数
     * @return 响应结果
     */
    @PostMapping("/wechat/callback")
    public String wechatCallback(@RequestBody Map<String, String> params) {
        log.info("收到微信回调，params: {}", params);

        if (params == null || params.isEmpty()) {
            log.warn("微信回调参数为空");
            return "<xml><return_code><![CDATA[FAIL]]></return_code></xml>";
        }

        try {
            String orderNo = params.get("out_trade_no");
            String transactionId = params.get("transaction_id");
            String returnCode = params.get("return_code");

            // 验证签名（实际应调用微信验签接口）
            boolean signVerified = verifyWechatSign(params);
            if (!signVerified) {
                log.error("微信签名验证失败");
                return "<xml><return_code><![CDATA[FAIL]]></return_code></xml>";
            }

            // 处理支付成功
            if ("SUCCESS".equals(returnCode)) {
                paymentUseCase.handlePaymentSuccess(orderNo, transactionId, 1); // 1-微信
                return "<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>";
            }

            log.info("微信回调处理完成，orderNo: {}, code: {}", orderNo, returnCode);
            return "<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>";

        } catch (Exception e) {
            log.error("微信回调处理失败", e);
            return "<xml><return_code><![CDATA[FAIL]]></return_code></xml>";
        }
    }

    /**
     * 验证支付宝签名
     *
     * @param params 回调参数
     * @return 验证结果
     */
    private boolean verifyAlipaySign(Map<String, String> params) {
        // TODO: 实际应调用支付宝验签接口
        // 这里暂时返回 true，实际实现需要：
        // 1. 获取支付宝公钥
        // 2. 按照支付宝验签算法验证签名
        // 3. 验证回调来源 IP
        return true;
    }

    /**
     * 验证微信签名
     *
     * @param params 回调参数
     * @return 验证结果
     */
    private boolean verifyWechatSign(Map<String, String> params) {
        // TODO: 实际应调用微信验签接口
        // 这里暂时返回 true，实际实现需要：
        // 1. 获取微信 API v3 密钥
        // 2. 验证微信签名
        // 3. 验证回调来源 IP
        return true;
    }
}
