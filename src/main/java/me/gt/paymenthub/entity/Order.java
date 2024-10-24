package me.gt.paymenthub.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import me.gt.paymenthub.constant.PaymentType;
import org.hibernate.annotations.Comment;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Schema(description = "訂單編號", requiredMode = Schema.RequiredMode.REQUIRED)
    private String orderId;

    @Schema(description = "商品名稱 有多筆時用#分隔", example = "商品一號#商品二號", requiredMode = Schema.RequiredMode.REQUIRED)
    private String itemName;

    @Schema(description = "交易描述", example = "範例商品描述", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tradeDesc;

    @Schema(description = "總金額", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer totalAmount;

    @Schema(description = "付款方式" , example = "ALL", requiredMode = Schema.RequiredMode.REQUIRED)
    private PaymentType paymentType;

}
