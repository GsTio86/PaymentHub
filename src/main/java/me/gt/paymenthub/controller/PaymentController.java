package me.gt.paymenthub.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.QueryTradeInfoObj;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.gt.paymenthub.constant.PaymentStatus;
import me.gt.paymenthub.entity.Payment;
import me.gt.paymenthub.service.PaymentService;
import me.gt.paymenthub.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/payment")
@Tag(name = "付款 API", description = "管理金流付款的操作")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Operation(summary = "查詢綠界金流交易資料")
    @GetMapping(value = "/ecpay/tradeinfo/{id}")
    public ResponseEntity<String> getEcpayTradeInfo(@PathVariable("id") String paymentId) {
        AllInOne aio = new AllInOne("");
        QueryTradeInfoObj queryTradeInfoObj = new QueryTradeInfoObj();
        queryTradeInfoObj.setMerchantTradeNo(paymentId);

        String tradeInfo = aio.queryTradeInfo(queryTradeInfoObj);
        try {
            return ResponseEntity.ok(StringUtils.ecpayTradeInfoFormatJson(tradeInfo));
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body("錯誤|" + e.getMessage());
        }
    }

    @Operation(summary = "查詢所有付款資料")
    @GetMapping(value = "/payments")
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @Operation(summary = "查詢指定狀態的付款資料")
    @GetMapping(value = "/payments/status/{status}")
    public ResponseEntity<List<Payment>> getAllPaymentsByStatus(@PathVariable("status") PaymentStatus status) {
        return ResponseEntity.ok(paymentService.getAllPaymentsByStatus(status.getName()));
    }

    @Operation(summary = "查詢指定付款方式的付款資料")
    @GetMapping(value = "/payments/type/{type}")
    public ResponseEntity<List<Payment>> getAllPaymentsByType(@PathVariable("type") String type) {
        return ResponseEntity.ok(paymentService.getAllPaymentsByType(type));
    }

    @Operation(summary = "查詢指定商品編號的付款資料")
    @GetMapping(value = "/payments/order/{orderId}")
    public ResponseEntity<Payment> getPaymentByOrderId(@PathVariable("orderId") String orderId) {
        return ResponseEntity.ok(paymentService.getPaymentByOrderId(orderId));
    }

    @Operation(summary = "更新付款狀態")
    @PutMapping(value = "/payments/{id}")
    public ResponseEntity<String> updatePaymentStatus(@PathVariable("id") String id, @RequestParam("status") PaymentStatus status) {
        paymentService.updatePaymentStatus(id, status);
        return ResponseEntity.ok("付款狀態更新成功");
    }

    @Operation(summary = "刪除付款資料")
    @DeleteMapping(value = "/payments/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable("id") String id) {
        paymentService.deletePayment(id);
        return ResponseEntity.ok("付款資料刪除成功");
    }


}
