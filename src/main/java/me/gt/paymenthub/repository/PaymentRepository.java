package me.gt.paymenthub.repository;

import me.gt.paymenthub.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, String> {

    List<Payment> findByOrderId(String orderId);

    List<Payment> findByType(String type);
    List<Payment> findByStatus(String status);

    @Modifying
    @Query(value = "update Payment p set p.type = ?2 where p.id = ?1")
    void updateStatusById(String id, String status);
}
