package com.example.financeapp.modules.share;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}/performance")
    public ResponseEntity<List> getSharePrice(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(shareService.getSharePrice(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Share>> findAllShareByCategory(@PathVariable String category) throws Exception {
        return ResponseEntity.ok(shareService.findAllShareByCategory(category));
    }

    @PostMapping
    public ResponseEntity createNewShare() throws Exception {
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMatchedOrder(@PathVariable Long id) throws Exception {
        shareService.deleteOrder(id);
        return ResponseEntity.ok("Success");
    }

}
