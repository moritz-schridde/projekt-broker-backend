package com.example.financeapp.modules.share;

import com.example.financeapp.modules.share.response.PerformanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/share")
public class ShareController {

    private final ShareService shareService;

    @Autowired
    public ShareController(ShareService shareService) {
        this.shareService = shareService;
    }


    @GetMapping
    public ResponseEntity<List<Share>> findAllShare() throws Exception {
        return ResponseEntity.ok(shareService.findAllShare());
    }

    @GetMapping("/performance")
    public ResponseEntity<ArrayList<PerformanceResponse>> getSharePrices() {
        ArrayList<PerformanceResponse> performance = new ArrayList<>();
        performance.add(new PerformanceResponse());
        return ResponseEntity.ok(performance);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Share>> findAllShareByCategory(@PathVariable String category) throws Exception {
        return ResponseEntity.ok(shareService.findAllShareByCategory(category));
    }

    @PostMapping
    public ResponseEntity<String> addNewShare(@RequestBody Long shareId) throws Exception {
        boolean success = true;
        if(success) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.badRequest().body("Failed");
        }
    }
    @DeleteMapping
    public ResponseEntity<String> deleteShareById(@RequestBody Long shareId) throws Exception {
        boolean success = true;
        shareService.deleteOrder(shareId);
        if(success) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.badRequest().body("Failed");
        }
    }

}
