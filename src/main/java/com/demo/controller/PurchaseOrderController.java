package com.demo.controller;

import com.demo.controller.request.PurchaseOrderCreationRequest;
import com.demo.controller.request.UpdateOrderStatusAndItemQuantityRequest;
import com.demo.controller.request.UpdateOrderStatusRequest;
import com.demo.controller.response.PurchaseOrderHistoryResponse;
import com.demo.controller.response.PurchaseOrderResponse;
import com.demo.model.PurchaseOrder;
import com.demo.service.PurchaseOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/purchase-order")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    @GetMapping(value = "/{purchaseOrderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PurchaseOrderResponse fetchOrder(@PathVariable(name = "purchaseOrderId") final Long id) {
        return PurchaseOrderResponse.fromEntity(purchaseOrderService.find(id));
    }

    @PostMapping(value = "/create-in-one-transaction", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PurchaseOrderResponse createNewOrder(
            @RequestBody @Valid final PurchaseOrderCreationRequest purchaseOrderRequest) {
        final PurchaseOrder purchaseOrder = purchaseOrderService.saveWithinTransaction(PurchaseOrderCreationRequest.toEntity(purchaseOrderRequest));
        return PurchaseOrderResponse.fromEntity(purchaseOrder);
    }

    @PostMapping(value = "/update-status", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PurchaseOrderResponse changeStatus(@RequestBody @Valid final UpdateOrderStatusRequest updateOrderStatusRequest) {
        return PurchaseOrderResponse.fromEntity(purchaseOrderService.changeStatus(updateOrderStatusRequest));
    }

    @PostMapping(value = "/update-status-and-item-quantity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PurchaseOrderResponse changeStatusAndOrderQuantity(@RequestBody @Valid final UpdateOrderStatusAndItemQuantityRequest updateOrderStatusAndItemQuantityRequest) {
        return PurchaseOrderResponse.fromEntity(purchaseOrderService.changeStatusAndOrderQuantity(updateOrderStatusAndItemQuantityRequest));
    }

    @PostMapping(value = "/update-status-and-item-quantity-in-separate-transactions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PurchaseOrderResponse changeStatusAndOrderQuantityInSeparateTransactions(@RequestBody @Valid final UpdateOrderStatusAndItemQuantityRequest updateOrderStatusAndItemQuantityRequest) {
        purchaseOrderService.changeStatus(UpdateOrderStatusRequest.builder()
                        .purchaseOrderId(updateOrderStatusAndItemQuantityRequest.getPurchaseOrderId())
                        .status(updateOrderStatusAndItemQuantityRequest.getStatus())
                .build());
        return PurchaseOrderResponse.fromEntity(purchaseOrderService.changeOrderQuantity(updateOrderStatusAndItemQuantityRequest));
    }

    @GetMapping(value = "/history/{purchaseOrderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PurchaseOrderHistoryResponse test(@PathVariable("purchaseOrderId") final Long purchaseOrderId) {
        return PurchaseOrderHistoryResponse.fromEntities(purchaseOrderService.getHistory(purchaseOrderId));
    }
}
