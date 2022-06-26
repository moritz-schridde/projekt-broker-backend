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

import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
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
    public ArrayList<DepotPerformanceResponse> getDepotPerformance() throws Exception {
        String email = userService.getCurrentUsersEmail();
        User currentUser = userService.getUserByEmail(email);
        Depot userDepot = currentUser.getMyDepot();
        Long depotId = userDepot.getId();
        List<DepotShareAmount> depotShares = userDepot.getMySharesAmount();

        ArrayList<DepotPerformanceResponse> response = new ArrayList<>();
        Double depotValuePerDay[] = new Double[30];
        for(int n=0; n < 30; n++) {
            depotValuePerDay[n] = 0.0;
        }
        LocalDate tomorrow = LocalDate.now(ZoneOffset.UTC).plusDays( 1 ) ;

        for(int j=0; j < depotShares.size(); j++) {
            Long shareId = depotShares.get(j).getShare().getId();
            for(int i=0; i < 30; i++) {
                OffsetDateTime odtStart = tomorrow.minusDays( (i + 1)).atTime( OffsetTime.MIN );
                OffsetDateTime odtStop = tomorrow.minusDays( i ).atTime( OffsetTime.MIN );
                Timestamp sqlStart = Timestamp.valueOf(odtStart.atZoneSameInstant(ZoneId.of("Europe/Berlin")).toLocalDateTime());
                Timestamp sqlStop = Timestamp.valueOf(odtStop.atZoneSameInstant(ZoneId.of("Europe/Berlin")).toLocalDateTime());
                ArrayList<Order> ordersByShareAndTimestamp = orderRepository.findAllByShareIdAndOrderClosedAtIsBetween(shareId, sqlStart, sqlStop);
                if(ordersByShareAndTimestamp.size() > 0) {
                    Order lastOrder = null;
                    boolean lastOrderFound = false;
                    boolean noOrderFound = false;
                    int iterator = 1;
                    while (lastOrderFound == false) {
                        if((ordersByShareAndTimestamp.size() - iterator) < 0) {
                            lastOrderFound = true;
                            noOrderFound = true;
                        }
                        else if(ordersByShareAndTimestamp.get((ordersByShareAndTimestamp.size() - iterator)).getAlreadySoldOrBought() > 0) {
                            lastOrder = ordersByShareAndTimestamp.get((ordersByShareAndTimestamp.size() - iterator));
                            lastOrderFound = true;
                        }
                        else {
                            iterator++;
                        }
                    }
                    if(noOrderFound) {
                        lastOrder = ordersByShareAndTimestamp.get((ordersByShareAndTimestamp.size() - 1));
                    }
                    Double sharePrice = lastOrder.getAgreedPrice();
                    ArrayList<Order> ordersBeforeTimestamp = orderRepository.findAllByDepotIdAndShareIdAndOrderClosedAtIsBefore(depotId, shareId, sqlStop);
                    Integer boughtShareAmount = 0;
                    for(int k=0; k < ordersBeforeTimestamp.size(); k++) {
                        if(ordersBeforeTimestamp.get(k).getOfferType().name() == "BUY") {
                            boughtShareAmount += ordersBeforeTimestamp.get(k).getAlreadySoldOrBought();
                        }
                    }
                    Double boughtSharesValue = boughtShareAmount * sharePrice;
                    depotValuePerDay[i] = depotValuePerDay[i] + boughtSharesValue;
                }
            }
        }
        for(int l=30; l > 0; l--) {
            OffsetDateTime odtDate = tomorrow.minusDays( (l + 1)).atTime( OffsetTime.MIN );
            Timestamp sqlDate = Timestamp.valueOf(odtDate.toLocalDate().atStartOfDay());
            DepotPerformanceResponse performancePerDay = new DepotPerformanceResponse();
            performancePerDay.setTimestamp(sqlDate);
            performancePerDay.setValue(depotValuePerDay[(l - 1)]);
            response.add(performancePerDay);
        }

        return response;
    }

    @Override
    public DepotResponse getUserDepotShareList() throws Exception {
        DepotResponse shareList = new DepotResponse();
        ArrayList<DepotShareInfoModel> shareInfoModelList = new ArrayList<DepotShareInfoModel>();

        String email = userService.getCurrentUsersEmail();
        User currentUser = userService.getUserByEmail(email);
        Depot userDepot = currentUser.getMyDepot();
        //Set Current CashValue of User
        Double depotCashValue = userDepot.getTotalCash();
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
            ArrayList<Order> boughtOrdersPerShare = new ArrayList<>();
            for(int i=0; i < orderList.size(); i++) {
                if(orderList.get(i).getOfferType().name() == "BUY") {
                    boughtOrdersPerShare.add(orderList.get(i));
                }
            }
            Double priceForShare = 0.0;
            Double divider = 0.0;
            for(int k=0; k < boughtOrdersPerShare.size(); k++) {
                priceForShare += boughtOrdersPerShare.get(k).getAgreedPrice() * boughtOrdersPerShare.get(k).getAlreadySoldOrBought();
                divider += boughtOrdersPerShare.get(k).getAlreadySoldOrBought();
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
