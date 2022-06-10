package com.example.financeapp.modules.depot;

import com.example.financeapp.modules.bank.Bank;
import com.example.financeapp.modules.bank.BankService;
import com.example.financeapp.modules.share.Share;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user/depot")
public class DepotController {

    @Autowired
    public DepotController() {

    }

    @GetMapping
    public ResponseEntity<HashMap> getSharesFromDepot() {
        HashMap<String, Integer> shareList = new HashMap<>();
        shareList.put("4141243132", 20);
        shareList.put("74192641", 40);
        shareList.put("87148014", 30);
        shareList.put("5782598732059", 50);
        return ResponseEntity.ok(shareList);
    }
}
