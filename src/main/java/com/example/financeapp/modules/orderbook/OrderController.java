package com.example.financeapp.modules.orderbook;

import com.example.financeapp.modules.orderbook.communication.models.OrderCommunicationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderCommunicationModel>> findAllOrders() throws Exception{
        return ResponseEntity.ok(orderService.findAllOrders());

    }


    @PostMapping

    public ResponseEntity<String> createOrder(@RequestBody @Valid OrderCommunicationModel request)throws Exception{
        Order order = orderService.createOrder(request);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrder(@PathVariable Long id) throws Exception {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order deleted Successfully");
    }
}
