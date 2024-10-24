package me.gt.paymenthub.service.impl;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;
import ecpay.payment.integration.exception.EcpayException;
import me.gt.paymenthub.constant.PaymentStatus;
import me.gt.paymenthub.constant.PaymentType;
import me.gt.paymenthub.entity.Order;
import me.gt.paymenthub.repository.PaymentRepository;
import me.gt.paymenthub.entity.Payment;
import me.gt.paymenthub.service.PaymentService;
import me.gt.paymenthub.util.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Hashtable;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Value("${payment.ecpay.return-url}")
    private String ecpayReturnUrl;

    @Value("${payment.ecpay.client-back-url}")
    private String ecpayClientBackUrl;

    @Value("${payment.ecpay.order-result-url}")
    private String ecpayOrderResultUrl;

    @Transactional
    @Override
    public String createEcpayAioPayment(Order order) throws EcpayException {
        AllInOne aio = new AllInOne("");
        AioCheckOutALL obj = new AioCheckOutALL();

        Payment payment = Payment.builder()
                .id(IdUtils.generatePaymentId())
                .orderId(order.getOrderId())
                .type(order.getPaymentType().getName())
                .amount(order.getTotalAmount())
                .build();

        obj.setMerchantTradeNo(payment.getId());
        obj.setMerchantTradeDate(payment.getCreatedAt());
        obj.setTradeDesc(order.getTradeDesc());
        obj.setItemName(order.getItemName());
        obj.setTotalAmount(String.valueOf(payment.getAmount()));

        PaymentType paymentType = order.getPaymentType();
        if (paymentType != PaymentType.ALL) {
            obj.setChoosePayment(paymentType.getId());
        }

        obj.setNeedExtraPaidInfo("N");
        obj.setReturnURL(ecpayReturnUrl);

        if (ecpayOrderResultUrl != null && !ecpayOrderResultUrl.isEmpty()) {
            obj.setOrderResultURL(ecpayOrderResultUrl);
        }
        if (ecpayClientBackUrl != null && !ecpayClientBackUrl.isEmpty()) {
            obj.setClientBackURL(ecpayClientBackUrl);
        }

        try {
            String form = aio.aioCheckOut(obj, null);
            paymentRepository.save(payment);
            return form;
        } catch (EcpayException e) {
            throw new EcpayException(e.getMessage());
        }
    }

    @Override
    public Payment getPaymentById(String id) {
        return paymentRepository.findById(id).orElse(null);
    }

    @Override
    public Payment getPaymentByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId).stream().findFirst().orElse(null);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public List<Payment> getAllPaymentsByType(String type) {
        return paymentRepository.findByType(type);
    }

    @Override
    public List<Payment> getAllPaymentsByStatus(String status) {
        return paymentRepository.findByStatus(status);
    }

    @Transactional
    @Override
    public String processEcpayPaymentResult(Hashtable<String, String> result) {
        AllInOne aio = new AllInOne("");
        boolean valid = aio.compareCheckMacValue(result); // 確認檢查碼是否正確
        if (valid) {
            boolean simulatePaid = false; // 是否為模擬付款
            if(result.containsKey("SimulatePaid")){
                simulatePaid = result.get("SimulatePaid").equals("1");
            }
            int rtnCode = 0; // 付款結果代碼
            if(result.containsKey("RtnCode")){
                rtnCode = Integer.parseInt(result.get("RtnCode"));
            }
            String rtnMsg = ""; // 付款結果訊息
            if(result.containsKey("RtnMsg")){
                rtnMsg = result.get("RtnMsg");
            }
            String merchantTradeNo = ""; // 特店交易編號
            if(result.containsKey("MerchantTradeNo")){
                merchantTradeNo = result.get("MerchantTradeNo");
            }
            switch (rtnCode) {
                case 1 -> {  // 付款成功
                    if (simulatePaid) {
                        updatePaymentStatus(merchantTradeNo, PaymentStatus.SIMULATING);
                    } else {
                        updatePaymentStatus(merchantTradeNo, PaymentStatus.COMPLETED);
                    }
                    return "OK";
                }
                case 10300066 -> { // 付款待確認
                    updatePaymentStatus(merchantTradeNo, PaymentStatus.CHECKING);
                    return "付款待確認";
                }
                default -> { // 付款失敗
                    updatePaymentStatus(merchantTradeNo, PaymentStatus.FAILED);
                    return rtnMsg;
                }
            }
        }
        return "檢查碼驗證失敗";
    }

    @Transactional
    @Override
    public void updatePaymentStatus(String id, PaymentStatus status) {
        paymentRepository.updateStatusById(id, status.getName());
    }

    @Override
    public void deletePayment(String id) {
        paymentRepository.deleteById(id);
    }
}
