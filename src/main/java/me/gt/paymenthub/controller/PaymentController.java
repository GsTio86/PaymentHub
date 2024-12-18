package me.gt.paymenthub.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.QueryTradeInfoObj;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.gt.paymenthub.constant.PaymentStatus;
import me.gt.paymenthub.dto.PaymentSearchDTO;
import me.gt.paymenthub.entity.Payment;
import me.gt.paymenthub.service.PaymentService;
import me.gt.paymenthub.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/create")
    public ModelAndView createPaymentPage() {
        return new ModelAndView("payment-create");
    }

    @GetMapping
    public ModelAndView getPaymentPage() {
        ModelAndView model = new ModelAndView("payment-manage");
        model.addObject("payments", paymentService.getPayments());
        return model;
    }

    @Operation(summary = "查詢所有付款資料")
    @GetMapping(value = "/payments")
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getPayments());
    }

    @Operation(summary = "查詢指定狀態的付款資料")
    @GetMapping(value = "/payments/status/{status}")
    public ResponseEntity<List<Payment>> getAllPaymentsByStatus(@PathVariable("status") PaymentStatus status) {
        return ResponseEntity.ok(paymentService.getPaymentsByStatus(status.getName()));
    }

    @Operation(summary = "查詢指定付款方式的付款資料")
    @GetMapping(value = "/payments/type/{type}")
    public ResponseEntity<List<Payment>> getAllPaymentsByType(@PathVariable("type") String type) {
        return ResponseEntity.ok(paymentService.getPaymentsByType(type));
    }

    @Operation(summary = "查詢指定商品編號的付款資料")
    @GetMapping(value = "/payments/order/{orderId}")
    public ResponseEntity<Payment> getPaymentByOrderId(@PathVariable("orderId") String orderId) {
        return ResponseEntity.ok(paymentService.getPaymentByOrderId(orderId));
    }

    @Operation(summary = "查詢指定條件的付款資料")
    @GetMapping(value = "/payments/search")
    public ResponseEntity<List<Payment>> searchPayments(
            @RequestParam(value = "paymentId", required = false) String paymentId,
            @RequestParam(value = "orderId", required = false) String orderId,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "status", required = false) PaymentStatus status) {
        PaymentSearchDTO searchDTO = new PaymentSearchDTO(paymentId, orderId, type, status);
        return ResponseEntity.ok(paymentService.getPaymentsByOrderIdStatusAndType(searchDTO));
    }

    @Operation(summary = "更新付款資料狀態")
    @PutMapping(value = "/payments/{id}")
    public ResponseEntity<String> updatePaymentStatus(@PathVariable("id") String id, @RequestParam("status") PaymentStatus status) {
        try {
            paymentService.updatePaymentStatus(id, status);
            return ResponseEntity.ok("付款資料更新成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("錯誤|" + e.getMessage());
        }
    }

    @Operation(summary = "刪除付款資料")
    @DeleteMapping(value = "/payments/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable("id") String id) {
        try {
            paymentService.deletePayment(id);
            return ResponseEntity.ok("付款資料刪除成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("錯誤|" + e.getMessage());
        }
    }

}
