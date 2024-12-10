package com.demo.controller.request;

import com.demo.model.PurchaseOrder;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateOrderStatusRequest {

    @NotNull
    private Long purchaseOrderId;
    @NotNull
    private PurchaseOrder.PurchaseOrderStatus status;
}
