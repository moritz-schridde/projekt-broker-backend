package com.example.financeapp.modules.orderbook;

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
        return ResponseEntity.ok(dummyOrderList);
    }


    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody @Valid OrderCreateRequest createRequest){
        boolean success = true;
        if (success) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.badRequest().body("Failed");
        }
    }

    @PutMapping
    public ResponseEntity<String> updateOrder(@RequestBody @Valid OrderUpdateRequest updateRequest) {
        boolean success = true;
        if (success) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.badRequest().body("Failed");
        }
    }

    @DeleteMapping ResponseEntity<String> deleteOrder(@RequestBody String[] orderId) {
        boolean success = true;
        if (success) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.badRequest().body("Failed");
        }
    }
}
