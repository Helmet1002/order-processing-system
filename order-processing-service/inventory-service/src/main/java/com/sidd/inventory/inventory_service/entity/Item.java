package com.sidd.inventory.inventory_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;

import java.time.Instant;

@Data
@Table(schema = "items")
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @PrimaryKey
    private String sku;

    private int stock;

    @Column(name = "updated_at")
    private Instant updatedAt;
}
