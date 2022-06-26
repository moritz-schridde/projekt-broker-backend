package com.example.financeapp.modules.orderbook;

import com.example.financeapp.modules.orderbook.communication.models.OrderCommunicationModel;

import java.util.List;

public interface OrderService {
    public List<OrderCommunicationModel> findAllOrders()throws Exception;
    public Order createOrder(OrderCommunicationModel request) throws Exception;
    public void deleteOrder(long id);
}
