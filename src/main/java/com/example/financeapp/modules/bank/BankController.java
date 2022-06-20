package com.example.financeapp.modules.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/bank")
public class BankController {

    private final BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping
    public ResponseEntity<Bank> getInformation() throws Exception {
        Bank dummyResponse = new Bank("DE640230807324872943", "mustermann", "max", 1000.00, "XX1HdW", "Verrechnungskonto", 74902649612431L);
        return ResponseEntity.ok(dummyResponse);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody double amount) throws Exception {
        Boolean success = true;
        if (success) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.badRequest().body("Failed");
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody double amount) throws Exception {
        Boolean success = true;
        if (success) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.badRequest().body("Failed");
        }
    }

}
