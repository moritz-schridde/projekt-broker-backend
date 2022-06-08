package com.example.financeapp.modules.orderbook;

import com.example.financeapp.modules.share.Share;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order getOrderById(Long id);
    List<Order> getAllByShareId(Long shareId);
    ArrayList<Order> findAllByState(String state);
    ArrayList<Order> findAllByStateAndOfferTypeAndOrderTypeOrderByTimestamp(Order.State state, Order.OrderType orderType, Order.OfferType offerType);
}
