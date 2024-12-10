package com.demo.controller.request;

import com.demo.model.Product;
import com.demo.model.PurchaseOrder;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateOrderStatusAndItemQuantityRequest {

    @NotNull
    private Long purchaseOrderId;
    @NotNull
    private PurchaseOrder.PurchaseOrderStatus status;

    @NotNull
    private Product productCode;

    @NotNull
    private int quantity;
}
