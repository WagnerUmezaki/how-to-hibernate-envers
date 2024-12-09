package com.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Table(name = "purchase_order")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class PurchaseOrder {

    public enum PurchaseOrderStatus {
        PENDING,
        WAITING_PAYMENT,
        PAID,
        CANCELED,
        DONE
    }

    @Id
    @SequenceGenerator(name = "sq_purchase_order_id", sequenceName = "sq_purchase_order_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_purchase_order_id")
    @Column(name = "id")
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PurchaseOrderStatus status;

    @Column(name = "user", nullable = false)
    private String user;

    @Version
    private int version;

    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

}
