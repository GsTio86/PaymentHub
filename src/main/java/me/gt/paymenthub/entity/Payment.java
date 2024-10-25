package me.gt.paymenthub.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import me.gt.paymenthub.constant.PaymentStatus;
import me.gt.paymenthub.util.DateUtils;
import org.hibernate.annotations.Comment;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @Column(name = "payment_id", updatable = false, nullable = false)
    @Comment("付款編號")
    @Schema(description = "付款編號", required = true)
    private String id;

    @Column(name = "payment_type", updatable = false, nullable = false)
    @Comment("付款方式")
    @Schema(description = "付款方式", required = true)
    private String type;

    @Column(name = "amount", updatable = false, nullable = false)
    @Comment("付款金額")
    @Schema(description = "付款金額", required = true)
    private Integer amount;

    @Column(name = "payment_status")
    @Comment("付款狀態")
    @Schema(description = "付款狀態")
    @Builder.Default
    private String status = PaymentStatus.PENDING.getName();

    @Column(name = "order_id", updatable = false, nullable = false)
    @Comment("訂單編號")
    @Schema(description = "訂單編號", required = true)
    private String orderId;

    @Column(name = "created_at", updatable = false)
    @Comment("建立時間")
    @Schema(description = "建立時間")
    @Builder.Default
    private String createdAt = DateUtils.getCurrentTime();

    @Column(name = "updated_at")
    @Comment("更新時間")
    @Schema(description = "更新時間")
    private String updatedAt;

}
