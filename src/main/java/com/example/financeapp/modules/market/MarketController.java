package com.example.financeapp.modules.market;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/market")
public class MarketController {
    @Autowired
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
