package com.example.financeapp.modules.orderbook;

import com.example.financeapp.modules.depot.Depot;
import com.example.financeapp.modules.depot.DepotRepository;
import com.example.financeapp.modules.depot.DepotShareAmount;
import com.example.financeapp.modules.depot.DepotShareAmountRepository;
import com.example.financeapp.modules.share.Share;
import com.example.financeapp.modules.share.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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


    @Override
    public List<Order> findAllOrders() {
        List<Order> orders =orderRepository.findAll();
        List<Order> orderResponse = new ArrayList<>();
        orders.forEach(order -> orderResponse.add(order));
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
    ArrayList<Order> sellOrders= new ArrayList<>();
    ArrayList<Order> buyOrders=new ArrayList<>();
    ArrayList<Order> buyMaxPrice = new ArrayList<>();
    ArrayList<Order> buyMarket = new ArrayList<>();
    ArrayList<Order> sellMinPrice = new ArrayList<>();
    ArrayList<Order> sellMarket = new ArrayList<>();

    @Override
    public Order createOrder(Order request) throws Exception {
        long shareId = request.getShareId();
        double referenzpreis = shareRepository.getShareById(shareId).getPrice();
        orderRepository.save(request);


        //Check if Orderbook is already loaded
        //if not then load and sort into lists accordingly
        //if(Order.reloadOrderbook){

        ArrayList<Order> test = orderRepository.findAllByState(Order.State.OPEN);
        ArrayList<Order> test2 = orderRepository.findAllByOrderType(Order.OrderType.MARKETORDER);
        ArrayList<Order> test3 = orderRepository.findAllByStateAndOrderType(Order.State.OPEN ,Order.OrderType.MARKETORDER);





        matchinAlgo(shareId);


        return request;
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
            else if(buyMaxPrice!=null){
                double highestbid=0.0;
                Order bestBuyOrder=null;
                for (Order order : buyMaxPrice) {
                    if (order.getMaxMinPreis() > highestbid) {
                        highestbid = order.getMaxMinPreis();
                        bestBuyOrder= order;
                    }
                }
                sellMarket.remove(0); //Orders aus buch löschen
                buyMaxPrice.remove(bestBuyOrder);

                //Verkäufer verkauft zu jedem Preis und daher wird er mit dem Käufer gematcht der das meiste bietet.
                executeOrder(highestbid, sellOrderId,bestBuyOrder.getId());
                matchinAlgo(shareId); //matching Algo erneut anstoßen falls durch neuen refP neue Matches möglich sind

            } //elseif
        }//if

        else if(!buyMarket.isEmpty()){
            long buyOrderId = buyMarket.get(0).getId();
            if(sellMinPrice!=null){
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
        //TODO total numbers of shares anpassen und was ist mit total value


        Order sellOrder = orderRepository.getOrderById(sellOrderId);
        Order buyOrder= orderRepository.getOrderById(buyOrderId);


        int orderVolume;
        if (sellOrder.getCount()<buyOrder.getCount()){
            orderVolume = sellOrder.getCount();
            sellOrder.setState(Order.State.CLOSED);
            buyOrder.setCount(buyOrder.getCount()-orderVolume);
        }else if (sellOrder.getCount()>buyOrder.getCount()) {
            orderVolume= buyOrder.getCount();
            buyOrder.setState(Order.State.CLOSED);
            sellOrder.setCount(sellOrder.getCount()-orderVolume);
        }else {
            orderVolume= buyOrder.getCount();
            buyOrder.setState(Order.State.CLOSED);
            sellOrder.setState(Order.State.CLOSED);
        }






        Depot sellDepot = depotRepository.getById(sellOrder.getDepotId());
        sellDepot.setTotalCash(sellDepot.getTotalCash()+(orderVolume*refP));

        Share shareToSell = shareRepository.getShareById(sellOrder.getShareId());
        DepotShareAmount sellDepotShareAmount = depotShareAmountRepository.findDepotShareAmountByDepotAndShare(sellDepot, shareToSell);
        sellDepotShareAmount.setAmount(sellDepotShareAmount.getAmount()-orderVolume);
        if (sellDepotShareAmount.getAmount()<0){
            throw new Exception("negative Share amount");
        }






        Depot buyDepot = depotRepository.getById(buyOrder.getDepotId());
        buyDepot.setTotalCash(buyDepot.getTotalCash()-(orderVolume*refP));
        if(buyDepot.getTotalCash()<0){
            throw  new Exception("nicht genug geld um die Aktien zu kaufen");
        }

        // depot share amout suchen und hochzählen wenn nciht vorhanden neu anlegen
        DepotShareAmount buyDepotShareAmount = depotShareAmountRepository.findDepotShareAmountByDepotAndShare(buyDepot, shareToSell);
        if(buyDepotShareAmount==null){
            buyDepotShareAmount = new DepotShareAmount(shareToSell, buyDepot, 0);
        }
        buyDepotShareAmount.setAmount(buyDepotShareAmount.getAmount()+orderVolume);






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
