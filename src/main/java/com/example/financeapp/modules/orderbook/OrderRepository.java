package com.example.financeapp.modules.orderbook;

import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByDepotIdAndShareId(Long depotId, Long shareId);
    List<Order> findAllByTimestampIsBetween(Timestamp timeBegin, Timestamp timeEnd);
    List<Order> findAllByTimestampAfterAndTimestampBefore(Timestamp timeBegin, Timestamp timeEnd);
}
