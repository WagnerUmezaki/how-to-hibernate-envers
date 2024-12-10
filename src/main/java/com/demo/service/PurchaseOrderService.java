package com.demo.service;

import com.demo.controller.request.UpdateOrderStatusAndItemQuantityRequest;
import com.demo.controller.request.UpdateOrderStatusRequest;
import com.demo.model.PurchaseOrder;
import com.demo.repository.PurchaseOrderRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final EntityManager entityManager;

    public PurchaseOrder find(final Long id) {
        return purchaseOrderRepository.findById(id).orElseThrow();
    }

    @Transactional
    public PurchaseOrder saveWithinTransaction(final PurchaseOrder purchaseOrder) {
        return purchaseOrderRepository.save(purchaseOrder);
    }

    @Transactional
    public PurchaseOrder changeStatus(final UpdateOrderStatusRequest updateOrderStatusRequest) {
        final PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(updateOrderStatusRequest.getPurchaseOrderId())
                .orElseThrow();

        if (purchaseOrder.getStatus().equals(updateOrderStatusRequest.getStatus())) {
           throw new IllegalArgumentException("Order already in this status");
        }

        purchaseOrder.setStatus(updateOrderStatusRequest.getStatus());

        return purchaseOrderRepository.save(purchaseOrder);
    }

    @Transactional
    public PurchaseOrder changeStatusAndOrderQuantity(final UpdateOrderStatusAndItemQuantityRequest updateOrderStatusRequest) {
        final PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(updateOrderStatusRequest.getPurchaseOrderId())
                .orElseThrow();

        if (purchaseOrder.getStatus().equals(updateOrderStatusRequest.getStatus())) {
            throw new IllegalArgumentException("Order already in this status");
        }

        purchaseOrder.setStatus(updateOrderStatusRequest.getStatus());
        purchaseOrder.getPurchaseOrderItems().forEach(purchaseOrderItem -> {
            if(purchaseOrderItem.getItemCode().equals(updateOrderStatusRequest.getProductCode())) {
                purchaseOrderItem.setQuantity(updateOrderStatusRequest.getQuantity());
            }
        });

        return purchaseOrderRepository.save(purchaseOrder);
    }

    @Transactional
    public PurchaseOrder changeOrderQuantity(final UpdateOrderStatusAndItemQuantityRequest updateOrderStatusRequest) {
        final PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(updateOrderStatusRequest.getPurchaseOrderId())
                .orElseThrow();

        purchaseOrder.getPurchaseOrderItems().forEach(purchaseOrderItem -> {
            if(purchaseOrderItem.getItemCode().equals(updateOrderStatusRequest.getProductCode())) {
                purchaseOrderItem.setQuantity(updateOrderStatusRequest.getQuantity());
            }
        });

        return purchaseOrderRepository.save(purchaseOrder);
    }

    public List<PurchaseOrder> getHistory(Long purchaseOrderId) {
        final AuditReader auditReader = AuditReaderFactory.get(entityManager);

        final List<Number> revisions = auditReader.getRevisions(PurchaseOrder.class, purchaseOrderId);

        if (revisions.isEmpty()) {
            return List.of();
        }

        final List<Object[]> results = auditReader.createQuery()
                .forRevisionsOfEntity(PurchaseOrder.class, false, false)
                .add(AuditEntity.id().eq(purchaseOrderId))
                .getResultList();

        return results.stream()
                .map(element -> (PurchaseOrder) element[0])
                .toList();
    }
}
