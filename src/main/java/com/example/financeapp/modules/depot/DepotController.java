package com.example.financeapp.modules.depot;

import com.example.financeapp.modules.depot.responses.DepotResponse;
import com.example.financeapp.modules.depot.responses.DepotPerformanceResponse;
import com.example.financeapp.modules.depot.responses.DepotShareInfoModel;
import com.example.financeapp.modules.share.Share;
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
        DepotResponse response = new DepotResponse();
        DepotShareInfoModel[] shareInfos = new DepotShareInfoModel[5];
        DepotShareInfoModel shareInfoModel = new DepotShareInfoModel();
        shareInfoModel.setShare(new Share());
        shareInfos[0] = shareInfoModel;
        response.setShares(shareInfos);
        shareList.add(response);
        return ResponseEntity.ok(shareList);
    }

    @GetMapping("/performance")
    public ResponseEntity<ArrayList<DepotPerformanceResponse>> getPerformanceForDepot() {
        ArrayList<DepotPerformanceResponse> shareList = new ArrayList<>();
        shareList.add(new DepotPerformanceResponse());
        return ResponseEntity.ok(shareList);
    }
}
