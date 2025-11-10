package com.sidd.inventory.inventory_service.repository;

import com.sidd.inventory.inventory_service.entity.ProcessedEvent;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedEventRepository extends CassandraRepository<ProcessedEvent, String> {
}
