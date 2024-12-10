package com.demo.controller;

import com.demo.controller.request.PurchaseOrderCreationRequest;
import com.demo.controller.response.PurchaseOrderResponse;
import com.demo.model.PurchaseOrder;
import com.demo.service.PurchaseOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchase-order")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    @PostMapping("/create-in-one-transaction")
    public PurchaseOrderResponse createNewOrder(
            @RequestBody @Valid final PurchaseOrderCreationRequest purchaseOrderRequest) {
        final PurchaseOrder purchaseOrder = purchaseOrderService.saveWithinTransaction(PurchaseOrderCreationRequest.toEntity(purchaseOrderRequest));
        return PurchaseOrderResponse.fromEntity(purchaseOrder);
    }

}
