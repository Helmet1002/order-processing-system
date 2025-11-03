package com.sidd.inventory.inventory_service.service;

import com.sidd.inventory.inventory_service.entity.ProcessedEvent;
import com.sidd.inventory.inventory_service.repository.ProcessedEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InventoryConsumer {

    private final ProcessedEventRepository processedEventRepository;
    private final InventoryService inventoryService;

    @KafkaListener(
            topics = "orders.created",
            groupId = "inventory-service",
            containerFactory = "inventoryKafkaListenerFactory"
    )
    public void consume(Map<String, Object> event, Acknowledgment ack) {

        String eventId = (String) event.get("eventId");

        if (processedEventRepository.existsById(eventId)) {
            ack.acknowledge();
            return;
        }

        Map<String, Object> payload = (Map<String, Object>) event.get("payload");
        List<Map<String, Object>> items = (List<Map<String, Object>>) payload.get("items");

        for (Map<String, Object> item : items) {
            String sku = (String) item.get("sku");
            int qty = (Integer) item.get("qty");
            inventoryService.decrementStock(sku, qty);
        }

        processedEventRepository.save(new ProcessedEvent(eventId, Instant.now()));

        ack.acknowledge();
    }
}
