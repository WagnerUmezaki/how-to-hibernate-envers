package com.demo.controller.request;

import com.demo.model.Product;
import com.demo.model.PurchaseOrder;
import com.demo.model.PurchaseOrderItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseOrderCreationRequest {

    public static PurchaseOrder toEntity(final PurchaseOrderCreationRequest creationRequest) {
        final PurchaseOrder purchaseOrder = PurchaseOrder.builder()
                .code(UUID.randomUUID().toString())
                .status(PurchaseOrder.PurchaseOrderStatus.PENDING)
                .customer(creationRequest.getCustomer())
                .build();
        purchaseOrder.setPurchaseOrderItems(creationRequest.purchaseOrderItems.stream()
                .map(purchaseOrderItemCreationRequest -> PurchaseOrderItem.builder()
                        .purchaseOrder(purchaseOrder)
                        .itemCode(purchaseOrderItemCreationRequest.itemCode)
                        .quantity(purchaseOrderItemCreationRequest.quantity)
                        .build()).collect(Collectors.toList()));
        return purchaseOrder;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PurchaseOrderItemCreationRequest {
        @NotNull
        @NotEmpty
        private Product itemCode;
        @Min(1)
        private int quantity;
    }

    @NotNull
    @NotEmpty
    private String customer;
    @NotEmpty
    @NotNull
    private List<PurchaseOrderItemCreationRequest> purchaseOrderItems = new ArrayList<>();
}
