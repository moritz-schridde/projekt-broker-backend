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
    public ResponseEntity<ArrayList<DepotResponse>> getSharesFromDepot() {
        ArrayList<DepotResponse> shareList = new ArrayList<>();
        shareList.add(new DepotResponse());
        return ResponseEntity.ok(shareList);
    }
}
