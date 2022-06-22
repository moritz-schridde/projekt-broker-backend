package com.example.financeapp.modules.orderbook;

import com.example.financeapp.modules.orderbook.communication.models.OrderCommunicationModel;
import com.example.financeapp.modules.orderbook.communication.models.OrderInfoModel;
import com.example.financeapp.modules.share.Share;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/order")
public class OrderController {

    @GetMapping
    public ResponseEntity<ArrayList<OrderCommunicationModel>> findAllOrders() {
        ArrayList<OrderCommunicationModel> dummyOrderList = new ArrayList<>();
        OrderCommunicationModel response = new OrderCommunicationModel();
        Share exampleShareObject = new Share();
        OrderInfoModel exampleInfoModel = new OrderInfoModel();
        exampleInfoModel.setShare(exampleShareObject);
        response.setShare(exampleInfoModel);
        dummyOrderList.add(response);
        return ResponseEntity.ok(dummyOrderList);
    }


    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody @Valid OrderCommunicationModel createRequest) {
        boolean success = true;
        if (success) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.badRequest().body("Failed");
        }
    }

    @PutMapping
    public ResponseEntity<String> updateOrder(@RequestBody @Valid OrderCommunicationModel updateRequest) {
        boolean success = true;
        if (success) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.badRequest().body("Failed");
        }
    }

    @DeleteMapping ResponseEntity<String> deleteOrder(@RequestBody String[] orderId) {
        boolean success = true;
        if (success) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.badRequest().body("Failed");
        }
    }
}
