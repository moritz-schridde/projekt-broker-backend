package com.example.financeapp.modules.depot;

import com.example.financeapp.modules.depot.responses.DepotPerformanceResponse;
import com.example.financeapp.modules.depot.responses.DepotResponse;
import com.example.financeapp.modules.depot.responses.DepotShareInfoModel;
import com.example.financeapp.modules.orderbook.Order;
import com.example.financeapp.modules.orderbook.OrderEnums;
import com.example.financeapp.modules.orderbook.OrderRepository;
import com.example.financeapp.modules.user.User;
import com.example.financeapp.modules.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DepotServiceImplementation implements DepotService {

    private final UserService userService;
    private final OrderRepository orderRepository;

    @Autowired
    public DepotServiceImplementation(UserService userService, OrderRepository orderRepository) {
        this.userService = userService;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<DepotPerformanceResponse> getDepotPerformance() throws Exception {
        String email = userService.getCurrentUsersEmail();
        User currentUser = userService.getUserByEmail(email);
        return null;
    }

    @Override
    public DepotResponse getUserDepotShareList() throws Exception {
        DepotResponse shareList = new DepotResponse();
        ArrayList<DepotShareInfoModel> shareInfoModelList = new ArrayList<DepotShareInfoModel>();

        String email = userService.getCurrentUsersEmail();
        User currentUser = userService.getUserByEmail(email);
        Depot userDepot = currentUser.getMyDepot();
        //Set Current CashValue of User
        Integer depotCashValue = userDepot.getTotalCash();
        shareList.setAvailableMoney(depotCashValue);

        Long depotId = userDepot.getId();
        List<DepotShareAmount> depotShares = userDepot.getMySharesAmount();
        for(int j=0; j < depotShares.size(); j++) {
            DepotShareInfoModel shareInfoModel = new DepotShareInfoModel();
            //Set Share and Amount for each shareInfoModel
            shareInfoModel.setShare(depotShares.get(j).getShare());
            shareInfoModel.setAmount(depotShares.get(j).getAmount());

            //get Average Price for bought Share
            Long shareId = depotShares.get(j).getShare().getId();
            List<Order> orderList= orderRepository.findAllByDepotIdAndShareId(depotId,shareId);
            List<Order> boughtOrdersPerShare = null;
            for(int i=0; i < orderList.size(); i++) {
                if(orderList.get(i).getOfferType().equals(OrderEnums.OfferType.BUY) && orderList.get(i).getState().equals(OrderEnums.State.CLOSED)) {
                    boughtOrdersPerShare.add(orderList.get(i));
                }
            }
            Double priceForShare = 0.0;
            Double divider = 0.0;
            for(int k=0; k < boughtOrdersPerShare.size(); k++) {
                priceForShare += boughtOrdersPerShare.get(k).getMaxMinPreis() * boughtOrdersPerShare.get(k).getCount();
                divider += boughtOrdersPerShare.get(k).getCount();
            }
            priceForShare = priceForShare / divider;
            //Set average Price for shareInfoModel
            shareInfoModel.setPurchasePrice(priceForShare);
            shareInfoModelList.add(shareInfoModel);
        }
        shareList.setShares(shareInfoModelList);
        return shareList;
    }
}
