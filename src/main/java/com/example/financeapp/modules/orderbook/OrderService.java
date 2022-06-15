package com.example.financeapp.modules.orderbook;

import java.util.List;

public interface OrderService {
    public List<Order> findAllOrders();
    public Order createOrder(Order request);
    public void deleteOrder(long id);
}
