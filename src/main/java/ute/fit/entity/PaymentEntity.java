package ute.fit.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import ute.fit.model.StatusPayment;

@Entity
public class PaymentEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentID;
    private double amount;
    private LocalDateTime paymentDate;
    private String paymentMethod; // Cash, VNPAY, Card
    @Enumerated(EnumType.STRING)
    private StatusPayment status;
    private String transactionContent; // Ghi chú giao dịch hoặc mã VNPAY
    
    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
}
