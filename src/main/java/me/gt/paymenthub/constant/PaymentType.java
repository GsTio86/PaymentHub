package me.gt.paymenthub.constant;

public enum PaymentType {

    ALL("ALL", "不指定"),
    CREDIT("Credit","信用卡"),
    WEBATM("WebATM","網路轉帳"),
    ATM("ATM","ATM轉帳"),
    CVS("CVS","超商代碼"),
    BARCODE("BARCODE","超商條碼"),
    APPLEPAY("ApplePay","ApplePay");

    String id;
    String name;

    PaymentType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
