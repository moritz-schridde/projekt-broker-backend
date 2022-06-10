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
    public ResponseEntity<String> freezeShareByID(@RequestBody String shareId) {
        boolean success = true;
        if (success) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.badRequest().body("Failed");
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteFreezeSOfShareByID(@RequestBody String shareId) {
        boolean success = true;
        if (success) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.badRequest().body("Failed");
        }
    }
}
