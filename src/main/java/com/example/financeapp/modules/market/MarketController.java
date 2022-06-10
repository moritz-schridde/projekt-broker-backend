package com.example.financeapp.modules.market;

import com.example.financeapp.modules.orderbook.Order;
import com.example.financeapp.modules.orderbook.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/market")
public class MarketController {
    public MarketController() {

    }

    @GetMapping
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("online");
    }

    @PostMapping
    public ResponseEntity<String> freezeShareByID() {
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteFreezeSOfShareByID() {
        return ResponseEntity.ok("Success");
    }
}
