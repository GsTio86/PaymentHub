package me.gt.paymenthub.service;

import me.gt.paymenthub.constant.PaymentStatus;
import me.gt.paymenthub.dto.PaymentSearchDTO;
import me.gt.paymenthub.entity.Order;
import me.gt.paymenthub.entity.Payment;
import org.springframework.data.jpa.domain.Specification;

import java.util.Hashtable;
import java.util.List;

public interface PaymentService {

    String createEcpayAioPayment(Order order);

    Payment getPaymentById(String id);

    Payment getPaymentByOrderId(String orderId);

    List<Payment> getPayments();

    List<Payment> getPaymentsByType(String type);

    List<Payment> getPaymentsByStatus(String status);

    List<Payment> getPaymentsByOrderIdStatusAndType(PaymentSearchDTO searchDTO);

    Specification<Payment> getPaymentsSpec(PaymentSearchDTO searchDTO);

    EcpayResult processEcpayPaymentResult(Hashtable<String, String> result);

    void updatePaymentStatus(String id, PaymentStatus status);

    void deletePayment(String id);

    record EcpayResult(long statusCode, String message) {}

}
