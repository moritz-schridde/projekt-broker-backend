package com.example.financeapp.modules.orderbook;

import com.example.financeapp.modules.account.Account;
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
    public ResponseEntity<List<Order>> findAllOrders() {
        return ResponseEntity.ok(orderService.findAllOrders());
    }


    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody @Valid Order request){
        Order order = orderService.createOrder(request);
        return ResponseEntity.ok(order);
    }

}
