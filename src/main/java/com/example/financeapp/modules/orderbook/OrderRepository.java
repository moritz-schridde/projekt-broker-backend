package com.example.financeapp.modules.orderbook;

import com.example.financeapp.modules.share.Share;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByDepotIdAndShareId(Long depotId, Long shareId);
    Order getOrderById(Long id);
    List<Order> getAllByShareId(Long shareId);
    ArrayList<Order> findAllByState(Order.State state);
    ArrayList<Order> findAllByStateAndOrderTypeAndOfferTypeAndShareId(Order.State state, Order.OrderType orderType, Order.OfferType offerType, long shareId);
    ArrayList<Order> findAllByStateAndOrderType(Order.State state, Order.OrderType orderType);
    ArrayList<Order> findAllByOrderType(Order.OrderType orderType);
}
