package com.sidd.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sidd.order.controller.CreateOrderRequest;
import com.sidd.order.entity.Order;
import com.sidd.order.entity.OrderResponse;
import com.sidd.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper mapper;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest req) throws JsonProcessingException {
        Order o = new Order();
        o.setCustomerId(req.getCustomerId());
        o.setTotal(req.getTotal());
        o.setItemsJson(mapper.writeValueAsString(req.getItems()));
        o.setStatus("CREATED");
        o.setCreatedAt(Instant.now());

        o = orderRepository.save(o);

        Map<String, Object> event = Map.of(
            "eventId", UUID.randomUUID().toString(),
            "eventType", "ORDER_CREATED",
            "occurredAt", Instant.now().toString(),
            "payload", Map.of(
                "orderId", o.getId(),
                "customerId", o.getCustomerId(),
                "items", req.getItems(),
                "total", o.getTotal()
            )
        );

        kafkaTemplate.send("orders.created", o.getId().toString(), event);

        return new OrderResponse(o.getId(), o.getStatus());
    }
}
