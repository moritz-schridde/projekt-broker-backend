package com.example.financeapp.modules.orderbook;

import com.example.financeapp.modules.depot.Depot;
import com.example.financeapp.modules.depot.DepotRepository;
import com.example.financeapp.modules.depot.DepotShareAmount;
import com.example.financeapp.modules.depot.DepotShareAmountRepository;
import com.example.financeapp.modules.orderbook.communication.models.OrderCommunicationModel;
import com.example.financeapp.modules.share.Share;
import com.example.financeapp.modules.share.ShareRepository;
import com.example.financeapp.modules.user.User;
import com.example.financeapp.modules.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Component
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ShareRepository shareRepository;
    @Autowired
    DepotRepository depotRepository;
    @Autowired
    DepotShareAmountRepository depotShareAmountRepository;

    @Autowired
    UserService userService;


    @Override
    public List<OrderCommunicationModel> findAllOrders()throws Exception {
        List<Order> orders =orderRepository.findAll();
        List<OrderCommunicationModel> orderResponse = new ArrayList<>();


            orders.forEach(order -> {
                try {
                    Share share = shareRepository.findById(order.getShareId()).orElseThrow(()-> new Exception ("Share not found "));
                    orderResponse.add(new OrderCommunicationModel(order, share));
                }catch (Exception e ){
                    e.printStackTrace();
                }
            });

        return orderResponse;
    }


    @Override
    public void deleteOrder(long id) {
        try {
            Order order = orderRepository.getOrderById(id);
            orderRepository.delete(order);
        }catch (Exception e){e.printStackTrace();}
    }

    //declare and initialize needed Lists
    ArrayList<Order> buyMaxPrice = new ArrayList<>();
    ArrayList<Order> buyMarket = new ArrayList<>();
    ArrayList<Order> sellMinPrice = new ArrayList<>();
    ArrayList<Order> sellMarket = new ArrayList<>();

    @Override
    public Order createOrder(OrderCommunicationModel request) throws Exception {

        Order order = new Order(request);
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSZZ");
        df.setTimeZone(TimeZone.getTimeZone("UTC+1"));
        //df.format(new Date());
        String timestapm = df.format(new Date());
        order.setTimestamp2(timestapm);

        long shareId = request.getInfo().getShare().getId();

        String email = userService.getCurrentUsersEmail();
        User user = userService.getUserByEmail(email);
        long depotId = user.getMyDepot().getId();

        order.setDepotId(depotId);


        double referenzpreis = shareRepository.getShareById(shareId).getPrice();
        orderRepository.save(order);

        matchinAlgo(shareId);


        return order;
    }


    public void matchinAlgo(long shareId) throws Exception{

        buyMarket = orderRepository.findAllByStateAndOrderTypeAndOfferTypeAndShareId(Order.State.OPEN, Order.OrderType.MARKETORDER, Order.OfferType.BUY, shareId);

        sellMarket= orderRepository.findAllByStateAndOrderTypeAndOfferTypeAndShareId(Order.State.OPEN, Order.OrderType.MARKETORDER, Order.OfferType.SELL, shareId);

        buyMaxPrice = orderRepository.findAllByStateAndOrderTypeAndOfferTypeAndShareId(Order.State.OPEN, Order.OrderType.LIMITORDER, Order.OfferType.BUY, shareId);

        sellMinPrice =orderRepository.findAllByStateAndOrderTypeAndOfferTypeAndShareId(Order.State.OPEN, Order.OrderType.STOPORDER, Order.OfferType.SELL, shareId);


        double referenzpreis = shareRepository.getShareById(shareId).getPrice();

        if(!sellMarket.isEmpty()){
            long sellOrderId = sellMarket.get(0).getId();
            if (!buyMarket.isEmpty()){
                long buyOrderId = buyMarket.get(0).getId();
                buyMarket.remove(0);
                sellMarket.remove(0);
                executeOrder(referenzpreis, sellOrderId,buyOrderId);

                //ruft sich selber wieder auf um zu prüfen ob weitere Matches möglich sind
                matchinAlgo(shareId);
            }
            else if(!buyMaxPrice.isEmpty()){
                double highestbid=buyMaxPrice.get(0).getMaxMinPreis();
                Order bestBuyOrder=buyMaxPrice.get(0);
                for (Order order : buyMaxPrice) {
                    if (order.getMaxMinPreis() > highestbid) {
                        highestbid = order.getMaxMinPreis();
                        bestBuyOrder= order;
                    }
                }
                //sellMarket.remove(0); //Orders aus buch löschen
                //buyMaxPrice.remove(bestBuyOrder);

                //Verkäufer verkauft zu jedem Preis und daher wird er mit dem Käufer gematcht der das meiste bietet.
                executeOrder(highestbid, sellOrderId,bestBuyOrder.getId());
                matchinAlgo(shareId); //matching Algo erneut anstoßen falls durch neuen refP neue Matches möglich sind

            } //elseif
        }//if

        else if(!buyMarket.isEmpty()){
            long buyOrderId = buyMarket.get(0).getId();
            if(!sellMinPrice.isEmpty()){
                double lowestAcceptablePrice =sellMinPrice.get(0).getMaxMinPreis();
                Order sellOrder =sellMinPrice.get(0);
                for (Order order: sellMinPrice){
                    if(order.getMaxMinPreis()<lowestAcceptablePrice){
                        lowestAcceptablePrice=order.getMaxMinPreis();
                        sellOrder=order;
                    }
                }
                buyMarket.remove(0);
                sellMinPrice.remove(sellOrder);

                executeOrder(lowestAcceptablePrice,  sellOrder.getId(),buyOrderId);

                //matching Algo erneut anstoßen falls durch neuen refP neue Matches möglich sind
                matchinAlgo(shareId);
            }
        }

        else if(!sellMinPrice.isEmpty() & !buyMaxPrice.isEmpty()){
            double highestBid=buyMaxPrice.get(0).getMaxMinPreis();
            Order bestBuyOrder = buyMaxPrice.get(0);
            double lowestAcceptablePrice = sellMinPrice.get(0).getMaxMinPreis();
            Order bestSellOrder = sellMinPrice.get(0);

            for(Order order: sellMinPrice){
                if(order.getMaxMinPreis()<lowestAcceptablePrice){
                    lowestAcceptablePrice=order.getMaxMinPreis();
                    bestSellOrder=order;
                }
            }

            for (Order order : buyMaxPrice){
                if(order.getMaxMinPreis()> highestBid){
                    highestBid = order.getMaxMinPreis();
                    bestBuyOrder= order;
                }
            }

            if(highestBid> lowestAcceptablePrice){
                if( referenzpreis> highestBid){
                    executeOrder(highestBid,  bestSellOrder.getId(),bestBuyOrder.getId());

                }else{
                    executeOrder(referenzpreis,  bestSellOrder.getId(),bestBuyOrder.getId());
                }
                matchinAlgo(shareId);
            }
        }


    }




    public void executeOrder(double refP, long sellOrderId, long buyOrderId) throws Exception{
        // TODO was ist mit total value


        Order sellOrder = orderRepository.getOrderById(sellOrderId);
        Order buyOrder= orderRepository.getOrderById(buyOrderId);


        int orderVolume;
        if (sellOrder.getCount()<buyOrder.getCount()){
            orderVolume = sellOrder.getCount();
            sellOrder.setState(Order.State.CLOSED);
            sellOrder.setCount(sellOrder.getCount()-orderVolume);
            sellOrder.setAlreadySoldOrBought(sellOrder.getAlreadySoldOrBought()+orderVolume);
            buyOrder.setCount(buyOrder.getCount()-orderVolume);
            buyOrder.setAlreadySoldOrBought(buyOrder.getAlreadySoldOrBought()+orderVolume);
        }else if (sellOrder.getCount()>buyOrder.getCount()) {
            orderVolume= buyOrder.getCount();
            buyOrder.setState(Order.State.CLOSED);
            buyOrder.setCount(buyOrder.getCount()-orderVolume);
            sellOrder.setCount(sellOrder.getCount()-orderVolume);

            sellOrder.setAlreadySoldOrBought(sellOrder.getAlreadySoldOrBought()+orderVolume);
            buyOrder.setAlreadySoldOrBought(buyOrder.getAlreadySoldOrBought()+orderVolume);
        }else {
            orderVolume= buyOrder.getCount();
            buyOrder.setState(Order.State.CLOSED);
            sellOrder.setState(Order.State.CLOSED);

            buyOrder.setCount(buyOrder.getCount()-orderVolume);
            sellOrder.setCount(sellOrder.getCount()-orderVolume);

            sellOrder.setAlreadySoldOrBought(sellOrder.getAlreadySoldOrBought()+orderVolume);
            buyOrder.setAlreadySoldOrBought(buyOrder.getAlreadySoldOrBought()+orderVolume);
        }







        Depot sellDepot = depotRepository.getById(sellOrder.getDepotId());
        sellDepot.setTotalCash(sellDepot.getTotalCash()+(orderVolume*refP));

        Share shareToSell = shareRepository.getShareById(sellOrder.getShareId());
        DepotShareAmount sellDepotShareAmount = depotShareAmountRepository.findDepotShareAmountByDepotAndShare(sellDepot, shareToSell);

        sellDepotShareAmount.setAmount(sellDepotShareAmount.getAmount()-orderVolume);
        sellDepot.setTotalNumberShares(sellDepot.getTotalNumberShares()-orderVolume);
        if (sellDepotShareAmount.getAmount()<0){
            throw new Exception("negative Share amount");
        }






        Depot buyDepot = depotRepository.getById(buyOrder.getDepotId());
        buyDepot.setTotalCash(buyDepot.getTotalCash()-(orderVolume*refP));
        if(buyDepot.getTotalCash()<0){
            throw  new Exception(buyDepot.getTotalCash() + "#"+ buyOrder.getCount()+"#"+ "nicht genug geld um die Aktien zu kaufen");
        }

        // depot share amout suchen und hochzählen wenn nciht vorhanden neu anlegen
        DepotShareAmount buyDepotShareAmount = depotShareAmountRepository.findDepotShareAmountByDepotAndShare(buyDepot, shareToSell);
        if(buyDepotShareAmount==null){
            buyDepotShareAmount = new DepotShareAmount(shareToSell, buyDepot, 0);
        }
        buyDepotShareAmount.setAmount(buyDepotShareAmount.getAmount()+orderVolume);
        buyDepot.setTotalNumberShares(buyDepot.getTotalNumberShares()+orderVolume);




        buyOrder.setAgreedPrice(refP);
        buyOrder.setOrderClosedAt(new Timestamp(System.currentTimeMillis()));
        sellOrder.setAgreedPrice(refP);
        sellOrder.setOrderClosedAt(new Timestamp(System.currentTimeMillis()));



        sellOrder =orderRepository.save(sellOrder);
        buyOrder = orderRepository.save(buyOrder);
        sellDepot = depotRepository.save(sellDepot);
        buyDepot = depotRepository.save(buyDepot);
        sellDepotShareAmount = depotShareAmountRepository.save(sellDepotShareAmount);
        buyDepotShareAmount = depotShareAmountRepository.save(buyDepotShareAmount);

        //neuen ref preis setzen
        long shareId = sellOrder.getShareId();
        setNewSharePrice(shareId, refP);

    }


    public void setNewSharePrice(long shareId, double price) {
        try {
            Share share = shareRepository.findById(shareId).orElseThrow(()-> new Exception("share not found"));
            share.setPrice(price);
            shareRepository.save(share);
        }catch (Exception e ){e.printStackTrace();}


    }



}
