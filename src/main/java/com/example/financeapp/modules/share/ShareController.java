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

    @GetMapping("/{id}")
    public ResponseEntity<Share> findShare(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(shareService.findShare(id));
    }

    //NOT USED
    @GetMapping("/price")
    public ResponseEntity<List> getSharePrices() throws Exception {
        return ResponseEntity.ok(shareService.getSharePrices());
    }

    //NOT USED
    @GetMapping("/price/{id}")
    public ResponseEntity<List> getSharePrice(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(shareService.getSharePrice(id));
    }

    //NOT USED
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Share>> findAllShareByCategory(@PathVariable String category) throws Exception {
        return ResponseEntity.ok(shareService.findAllShareByCategory(category));
    }

    @PostMapping("/{id}")
    public ResponseEntity postShare(@RequestHeader Share share) throws Exception {
        shareService.postShare(share.getName(), share.getWkn(), share.getPrice(), share.getCategory());
        return ResponseEntity.ok("Order deleted Successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMatchedOrder(@PathVariable Long id) throws Exception {
        shareService.deleteOrder(id);
        return ResponseEntity.ok("Order deleted Successfully");
    }

}
