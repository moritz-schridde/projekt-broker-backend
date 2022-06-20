package com.example.financeapp.modules.share;

import com.example.financeapp.modules.share.responses.SharePerformanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

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

    @GetMapping("/{id}/performance")
    public ResponseEntity<ArrayList<SharePerformanceResponse>> getSharePrice(@PathVariable Long id) throws Exception {
        ArrayList<SharePerformanceResponse> sharelist = new ArrayList<>();
        sharelist.add(new SharePerformanceResponse());
        return ResponseEntity.ok(sharelist);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Share>> findAllShareByCategory(@RequestBody String category) throws Exception {
        return ResponseEntity.ok(shareService.findAllShareByCategory(category));
    }

    @PostMapping
    public ResponseEntity<String> createNewShare() throws Exception {
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMatchedOrder(@PathVariable Long id) throws Exception {
        shareService.deleteOrder(id);
        return ResponseEntity.ok("Success");
    }

}
