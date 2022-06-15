package com.example.financeapp.modules.orderbook;

import com.example.financeapp.modules.share.Share;
import com.example.financeapp.modules.share.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ShareRepository shareRepository;


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
    public Order createOrder(Order request) {
        long shareId = request.getShareId();
        double referenzpreis = shareRepository.getShareById(shareId).getPrice();


        //Check if Orderbook is already loaded
        //if not then load and sort into lists accordingly
        if(Order.reloadOrderbook){

            buyMarket = orderRepository.findAllByStateAndOfferTypeAndOrderTypeAndShareIdOrderByTimestamp(Order.State.OPEN, Order.OrderType.MARKETORDER, Order.OfferType.BUY, shareId);

            sellMarket= orderRepository.findAllByStateAndOfferTypeAndOrderTypeAndShareIdOrderByTimestamp(Order.State.OPEN, Order.OrderType.MARKETORDER, Order.OfferType.SELL, shareId);

            //TODO die anderen Listen vervollständigen 

            /*
            Order.setOrderBook(orderRepository.findAllByState("OPEN"));
            ArrayList<Order> orderBook = new ArrayList<>(Order.getOrderBook());

            orderBook.forEach(order -> {
                if (order.getOfferType()== Order.OfferType.BUY) {
                    buyOrders.add(order);
                }
                if (order.getOfferType()== Order.OfferType.SELL){
                    sellOrders.add(order);
                }});

            if (request.getOfferType() == Order.OfferType.BUY) {
                buyOrders.add(request);
            }
            if (request.getOfferType()== Order.OfferType.SELL){
                sellOrders.add(request);
            }

            buyOrders.forEach(order -> {
                if order.getOrderType()
            });*/

            Order.setReloadOrderbook(false);
        }

        orderRepository.save(request);



        //TODO den einkommenden request richtig einordnen

        if (request.getOfferType() == Order.OfferType.BUY) {
            buyOrders.add(request);
        }
        if (request.getOfferType()== Order.OfferType.SELL){
            sellOrders.add(request);
        }



        matchinAlgo(shareId, buyMarket, sellMarket, buyMaxPrice, sellMinPrice);


        return request;
    }


    public void matchinAlgo(long shareId, ArrayList<Order> buyMarketList, ArrayList<Order> sellMarketList, ArrayList<Order> buyMaxPriceList, ArrayList<Order> sellMinPriceList){
        double referenzpreis = shareRepository.getShareById(shareId).getPrice();

        if(sellMarketList!=null){
            long firstOrderId = sellMarketList.get(0).getId();
            if (buyMarketList!=null){
                long secondOrderId = buyMarketList.get(0).getId();
                buyMarketList.remove(0);
                sellMarketList.remove(0);
                executeOrder(shareId, firstOrderId,secondOrderId);

                //ruft sich selber wieder auf um zu prüfen ob weitere Matches möglich sind
                matchinAlgo(shareId, buyMarketList, sellMarketList, buyMaxPriceList, sellMinPriceList);
            }
            else if(buyMaxPriceList!=null){
                double highestbid=0.0;
                Order bestOrder=null;
                for (Order order : buyMaxPriceList) {
                    if (order.getMaxMinPreis() > highestbid) {
                        highestbid = order.getMaxMinPreis();
                        bestOrder= order;
                    }
                }
                sellMarketList.remove(0); //Orders aus buch löschen
                buyMaxPriceList.remove(bestOrder);

                //Verkäufer verkauft zu jedem Preis und daher wird er mit dem Käufer gematcht der das meiste bietet.
                executeOrder(highestbid, bestOrder.getId(), firstOrderId);
                matchinAlgo(shareId, buyMarketList, sellMarketList, buyMaxPriceList, sellMinPriceList); //matching Algo erneut anstoßen falls durch neuen refP neue Matches möglich sind

            } //elseif
        }//if

        else if(!buyMarketList.isEmpty()){
            long firstOrderId = buyMarketList.get(0).getId();
            if(sellMinPriceList!=null){
                double lowestAcceptablePrice =sellMinPriceList.get(0).getMaxMinPreis();
                Order bestOrder =sellMinPriceList.get(0);
                for (Order order: sellMinPriceList){
                    if(order.getMaxMinPreis()<lowestAcceptablePrice){
                        lowestAcceptablePrice=order.getMaxMinPreis();
                        bestOrder=order;
                    }
                }
                buyMarketList.remove(0);
                sellMinPriceList.remove(bestOrder);

                executeOrder(lowestAcceptablePrice, firstOrderId, bestOrder.getId());

                //matching Algo erneut anstoßen falls durch neuen refP neue Matches möglich sind
                matchinAlgo(shareId, buyMarketList, sellMarketList, buyMaxPriceList, sellMinPriceList);
            }
        }

        else if(!sellMinPriceList.isEmpty() & !buyMaxPriceList.isEmpty()){
            double highestBid=buyMaxPriceList.get(0).getMaxMinPreis();
            Order bestBuyOrder = buyMaxPriceList.get(0);
            double lowestAcceptablePrice = sellMinPriceList.get(0).getMaxMinPreis();
            Order bestSellOrder = sellMinPriceList.get(0);

            for(Order order: sellMinPriceList){
                if(order.getMaxMinPreis()<lowestAcceptablePrice){
                    lowestAcceptablePrice=order.getMaxMinPreis();
                    bestSellOrder=order;
                }
            }

            for (Order order : buyMaxPriceList){
                if(order.getMaxMinPreis()> highestBid){
                    highestBid = order.getMaxMinPreis();
                    bestBuyOrder= order;

                }
            }

            if(highestBid> lowestAcceptablePrice){
                if( referenzpreis> highestBid){
                    executeOrder(highestBid, bestBuyOrder.getId(), bestSellOrder.getId());

                }else{
                    executeOrder(referenzpreis, bestBuyOrder.getId(), bestSellOrder.getId());
                }
                matchinAlgo(shareId, buyMarketList, sellMarketList, buyMaxPriceList, sellMinPriceList);
            }
        }


    }




    public void executeOrder(double refP, long firstOrderId, long secondOrderId){
        //TODO geld abbuchen Aktein transferieren
        //geld vom depot abbuchen
        //aktien vom Verkäufer abziehen und dem Käufer gutschreiben


        //neuen ref preis setzen
        setNewSharePrice(firstOrderId, refP);

    }


    public void setNewSharePrice(long shareId, double price) {
        try {
            Share share = shareRepository.findById(shareId).orElseThrow(()-> new Exception("share not found"));
            share.setPrice(price);
            shareRepository.save(share);
        }catch (Exception e ){e.printStackTrace();}


    }



}
