package com.sidd.inventory.inventory_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import java.time.Instant;

@Data
@Table(name="processed_events")
@NoArgsConstructor
@AllArgsConstructor
public class ProcessedEvent {

    @PrimaryKey
    private String eventId;

    @Column(name="processed_at")
    private Instant processedAt;
}
