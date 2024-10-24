package me.gt.paymenthub.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class IdUtils {

    private static final DateTimeFormatter idDataFormat = DateTimeFormatter.ofPattern("HHmmssSSS");

    /*
     * 付款編號前綴
     */
    private static final String PAYMENT_PREFIX = "PAY";

    /*
     * 產生付款編號
     * 付款編號格式為 PAYMENT-{timestamp}-{uid}
     * @return 付款編號
     */
    public static String generatePaymentId() {
        return String.format("%s%s", PAYMENT_PREFIX, generateUidWithTimestamp());
    }

    /*
     * 產生唯一識別碼 包含時間戳記
     * 唯一識別碼格式為 {timestamp}-{uid}
     * @return 唯一識別碼
     */
    public static String generateUidWithTimestamp() {
        String timestamp = idDataFormat.format(LocalDateTime.now());
        String uid = generateUid();
        return String.format("%s%s", timestamp, uid);
    }

    /*
     * 產生唯一識別碼 只取前8碼
     * 唯一識別碼格式為 {uid}
     * @return 唯一識別碼
     */
    public static String generateUid() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

}
