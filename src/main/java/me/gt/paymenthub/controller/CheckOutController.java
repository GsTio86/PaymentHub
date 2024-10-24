package me.gt.paymenthub.controller;

import ecpay.payment.integration.exception.EcpayException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.gt.paymenthub.entity.Order;
import me.gt.paymenthub.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/checkout")
@Tag(name = "結帳 API", description = "處理訂單金流的操作")
public class CheckOutController {

    @Autowired
    private PaymentService paymentService;

    @Operation(summary = "建立綠界金流表單")
    @PostMapping("/ecpay")
    public ResponseEntity<String> createEcpayPaymentForm(@RequestBody Order order) {
        try {
            String form = paymentService.createEcpayAioPayment(order);
            return ResponseEntity.ok(form);
        } catch (EcpayException e) {
            return ResponseEntity.badRequest().body("錯誤|訂單建立失敗");
        }
    }

}
