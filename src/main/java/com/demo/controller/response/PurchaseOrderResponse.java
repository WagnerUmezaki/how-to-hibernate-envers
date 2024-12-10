package com.demo.controller.response;

import com.demo.model.Product;
import com.demo.model.PurchaseOrder;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderResponse {

    public static PurchaseOrderResponse fromEntity(PurchaseOrder purchaseOrder) {
        return PurchaseOrderResponse.builder()
                .id(purchaseOrder.getId())
                .customer(purchaseOrder.getCustomer())
                .status(purchaseOrder.getStatus())
                .items(purchaseOrder.getPurchaseOrderItems().stream()
                        .map(purchaseOrderItem -> PurchaseOrderItemResponse.builder()
                            .id(purchaseOrderItem.getId())
                            .itemCode(purchaseOrderItem.getItemCode())
                            .quantity(purchaseOrderItem.getQuantity())
                            .createdAt(purchaseOrderItem.getCreatedAt())
                            .updatedAt(purchaseOrderItem.getUpdatedAt())
                            .build()).toList())
                .version(purchaseOrder.getVersion())
                .createdAt(purchaseOrder.getCreatedAt())
                .updatedAt(purchaseOrder.getUpdatedAt())
                .build();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PurchaseOrderItemResponse {
        private Long id;
        private Product itemCode;
        private int quantity;
        private PurchaseOrder purchaseOrder;
        private Instant createdAt;
        private Instant updatedAt;
    }

    private Long id;
    private String customer;
    private PurchaseOrder.PurchaseOrderStatus status;
    private String user;
    private List<PurchaseOrderItemResponse> items = new ArrayList<>();
    private int version;
    private Instant createdAt;
    private Instant updatedAt;
}
