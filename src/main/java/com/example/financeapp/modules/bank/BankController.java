package com.example.financeapp.modules.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/bank")
public class BankController {

    private final BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping
    public ResponseEntity<List<Bank>> getInformation() throws Exception {
        //Bank dummyResponse = new Bank("DE640230807324872943", "mustermann", "max", 1000.00, "XX1HdW", "Verrechnungskonto", 74902649612431L);

        return ResponseEntity.ok(this.bankService.getBank());
    }

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody Map<String, Object> body) throws Exception {

        Boolean result = this.bankService.create(body);

        if (result) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.badRequest().body("Failed");
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody Map<String, Object> body) throws Exception {
        double amount = (double) body.get("amount");
        String iban = (String) body.get("kontoId");

        Boolean result = this.bankService.changeAmount(iban, amount, Bank.mode.WITHDRAW);

        if (result) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.badRequest().body("Failed");
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody Map<String, Object> body) throws Exception {
        double amount = (double) body.get("amount");
        String iban = (String) body.get("kontoId");

        Boolean result = this.bankService.changeAmount(iban, amount, Bank.mode.DEPOSIT);

        if (result) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.badRequest().body("Failed");
        }
    }

}
