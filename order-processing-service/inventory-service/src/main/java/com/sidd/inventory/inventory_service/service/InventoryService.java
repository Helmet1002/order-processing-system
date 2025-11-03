package com.sidd.inventory.inventory_service.service;

import com.sidd.inventory.inventory_service.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ItemRepository itemRepository;

    public void decrementStock(String sku, int qty) {

        boolean success = itemRepository.decrementStockAtomic(sku, qty);

        if (!success) {
            throw new RuntimeException("Not enough stock for SKU: " + sku);
        }
    }
}
