package com.demo.controller.response;

import com.demo.model.PurchaseOrder;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderHistoryResponse {

    public static PurchaseOrderHistoryResponse fromEntities(final List<PurchaseOrder> purchaseOrders) {
        return PurchaseOrderHistoryResponse.builder()
                .purchaseOrders(purchaseOrders.stream()
                        .map(PurchaseOrderResponse::fromEntity)
                        .toList())
                .build();
    }

    private List<PurchaseOrderResponse> purchaseOrders;
}
