package com.example.financeapp.modules.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank")
public class BankController {

    private final BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/{iban}")
    public ResponseEntity<Bank> withdraw(@PathVariable("iban") String iban) throws Exception {
        return ResponseEntity.ok(bankService.getBank(iban));
    }

    @PostMapping("/{iban}/withdraw")
    public ResponseEntity<Bank> withdraw(@PathVariable("iban") String iban, @RequestBody double amount) throws Exception {
        return ResponseEntity.ok(bankService.changeAmount(iban, amount, Bank.mode.WITHDRAW));
    }

    @PostMapping("/{iban}/deposit")
    public ResponseEntity<Bank> deposit(@PathVariable("iban") String iban, @RequestBody double amount) throws Exception {
        return ResponseEntity.ok(bankService.changeAmount(iban, amount, Bank.mode.DEPOSIT));
    }

}
