package com.sidd.inventory.inventory_service.repository;

import com.sidd.inventory.inventory_service.entity.Item;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CassandraRepository<Item, String> {

    @Query("UPDATE items SET stock = stock - :qty, updated_at = toTimestamp(now()) "
         + "WHERE sku = :sku IF stock >= :qty")
    boolean decrementStockAtomic(@Param("sku") String sku, @Param("qty") int qty);
}
