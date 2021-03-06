package com.example.financeapp.modules.bank;

import com.example.financeapp.modules.bank.models.BankRequestCommunicationModel;
import com.example.financeapp.modules.bank.models.BankRequestCommunicationModelCreate;
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
    public ResponseEntity<List<Bank>> getInformation() throws Exception {
        //Bank dummyResponse = new Bank("DE640230807324872943", "mustermann", "max", 1000.00, "XX1HdW", "Verrechnungskonto", 74902649612431L);

        return ResponseEntity.ok(this.bankService.getBank());
    }

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody BankRequestCommunicationModelCreate body) throws Exception {

        Boolean result = this.bankService.create(body);

        if (result) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.badRequest().body("Failed");
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody BankRequestCommunicationModel body) throws Exception {
        double amount = body.getAmount();
        String iban = body.getIban();

        Boolean result = this.bankService.changeAmount(iban, amount, Bank.mode.WITHDRAW);

        if (result) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.badRequest().body("Failed");
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody BankRequestCommunicationModel body) throws Exception {
        double amount = body.getAmount();
        String iban = body.getIban();

        Boolean result = this.bankService.changeAmount(iban, amount, Bank.mode.DEPOSIT);

        if (result) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.badRequest().body("Failed");
        }
    }

}
