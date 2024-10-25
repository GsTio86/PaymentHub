package me.gt.paymenthub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.gt.paymenthub.constant.PaymentStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSearchDTO {
    private String paymentId;
    private String orderId;
    private String type;
    private PaymentStatus status;

}
