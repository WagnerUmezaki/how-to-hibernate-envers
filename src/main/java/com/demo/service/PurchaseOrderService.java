package com.demo.service;

import com.demo.model.PurchaseOrder;
import com.demo.repository.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    @Transactional
    public PurchaseOrder saveWithinTransaction(final PurchaseOrder purchaseOrder) {
        return purchaseOrderRepository.save(purchaseOrder);
    }
}
