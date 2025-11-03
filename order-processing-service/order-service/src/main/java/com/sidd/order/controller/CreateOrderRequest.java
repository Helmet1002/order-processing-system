package com.sidd.order.controller;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateOrderRequest {
    private String customerId;
    private BigDecimal total;
    private List<ItemRequest> items;

    @Data
    public static class ItemRequest {
        private String sku;
        private int qty;
    }
}
