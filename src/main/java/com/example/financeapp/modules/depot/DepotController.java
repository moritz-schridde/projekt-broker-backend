package com.example.financeapp.modules.depot;

import com.example.financeapp.modules.depot.responses.DepotResponse;
import com.example.financeapp.modules.depot.responses.DepotPerformanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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

    @GetMapping("/performance")
    public ResponseEntity<ArrayList<DepotPerformanceResponse>> getPerformanceForDepot() {
        ArrayList<DepotPerformanceResponse> shareList = new ArrayList<>();
        shareList.add(new DepotPerformanceResponse());
        return ResponseEntity.ok(shareList);
    }
}
