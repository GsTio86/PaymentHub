package me.gt.paymenthub.service.impl;

import me.gt.paymenthub.constant.PaymentStatus;
import me.gt.paymenthub.entity.Payment;
import me.gt.paymenthub.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PaymentServiceImplTest {

    @Autowired
    private PaymentService service;

    @Test
    public void getPaymentByIdTest() {
        Payment payment = service.getPaymentById("PAY2219207979b7ce227");
        if (payment != null) {
            System.out.println("付款資料: " + payment);
        } else {
            System.out.println("找不到付款資料");
        }
    }

    @Test
    public void getPaymentByOrderIdTest() {
        Payment payment = service.getPaymentByOrderId("string");
        if (payment != null) {
            System.out.println("付款資料: " + payment);
        } else {
            System.out.println("找不到付款資料");
        }
    }

    @Test
    public void getPaymentsTest() {
        List<Payment> payments = service.getPayments();
        if (payments != null) {
            for (Payment payment : payments) {
                System.out.println("付款資料: " + payment);
            }
        } else {
            System.out.println("找不到付款資料");
        }
    }

    @Test
    public void getPaymentsByStatusTest() {
        List<Payment> payments = service.getPaymentsByStatus("待付款");
        if (payments != null) {
            for (Payment payment : payments) {
                System.out.println("付款資料: " + payment);
            }
        } else {
            System.out.println("找不到付款資料");
        }
    }

    @Test
    public void getPaymentsByTypeTest() {
        List<Payment> payments = service.getPaymentsByType("超商代碼");
        if (payments != null) {
            for (Payment payment : payments) {
                System.out.println("付款資料: " + payment);
            }
        } else {
            System.out.println("找不到付款資料");
        }
    }

    @Test
    public void updatePaymentStatusTest() {
        service.updatePaymentStatus("PAY23221021804281032", PaymentStatus.COMPLETED);
    }

    @Test
    public void deletePaymentTest() {
        service.deletePayment("PAY22222694461c2adea");
    }


}