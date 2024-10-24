package me.gt.paymenthub.service;

import me.gt.paymenthub.constant.PaymentStatus;
import me.gt.paymenthub.entity.Order;
import me.gt.paymenthub.entity.Payment;

import java.util.Hashtable;
import java.util.List;

public interface PaymentService {

    String createEcpayAioPayment(Order order);

    Payment getPaymentById(String id);

    Payment getPaymentByOrderId(String orderId);

    List<Payment> getAllPayments();

    List<Payment> getAllPaymentsByType(String type);

    List<Payment> getAllPaymentsByStatus(String status);

    String processEcpayPaymentResult(Hashtable<String, String> result);

    void updatePaymentStatus(String id, PaymentStatus status);

    void deletePayment(String id);

}
