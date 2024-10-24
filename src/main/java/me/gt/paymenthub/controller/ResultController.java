package me.gt.paymenthub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.gt.paymenthub.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/result")
@Tag(name = "付款結果 API", description = "處理付款完成的金流操作")
public class ResultController {

    @Autowired
    private PaymentService paymentService;

    @Operation(summary = "處理綠界金流付款結果")
    @PostMapping(value = "/ecpay", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> ecpayPaymentResult(@RequestParam Map<String, String> paymentResult) {
        String msg = paymentService.processEcpayPaymentResult(new Hashtable(paymentResult));
        if (msg.equals("OK")) {
            return ResponseEntity.ok("1|OK");
        } else {
            return ResponseEntity.badRequest().body("錯誤|" + msg);
        }
    }

}
