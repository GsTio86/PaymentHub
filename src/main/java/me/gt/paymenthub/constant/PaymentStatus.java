package me.gt.paymenthub.constant;

public enum PaymentStatus {
    PENDING("待付款"),
    CHECKING("待確認"),
    SIMULATING("模擬付款"),
    REFUND("已退款"),
    COMPLETED("已付款"),
    FAILED("付款失敗");

    String name;

    PaymentStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
