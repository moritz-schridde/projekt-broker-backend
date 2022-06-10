package com.example.financeapp.modules.orderbook;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/order")
public class OrderController {

    @GetMapping
    public ResponseEntity<ArrayList<OrderResponse>> findAllOrders() {
        ArrayList<OrderResponse> dummyOrderList = new ArrayList<>();
        dummyOrderList.add(new OrderResponse());
        dummyOrderList.add(new OrderResponse());
        dummyOrderList.add(new OrderResponse());
        dummyOrderList.add(new OrderResponse());
        return ResponseEntity.ok(dummyOrderList);
    }


    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody @Valid Order request){
        boolean success = true;
        if (success) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.badRequest().body("Failed");
        }
    }

    @PutMapping
    public ResponseEntity<String> updateOrder() {
        boolean success = true;
        if (success) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.badRequest().body("Failed");
        }
    }

    @DeleteMapping ResponseEntity<String> deleteOrder() {
        boolean success = true;
        if (success) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.badRequest().body("Failed");
        }
    }
}
