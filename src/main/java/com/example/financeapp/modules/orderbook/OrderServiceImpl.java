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

            buyMarket = orderRepository.findAllByStateAndOfferTypeAndOrderTypeAndShareIdOrderByTimestamp(Order.State.OPEN, Order.OrderType.MARKETORDER, Order.OfferType.BUY, shareId);

            sellMarket= orderRepository.findAllByStateAndOfferTypeAndOrderTypeAndShareIdOrderByTimestamp(Order.State.OPEN, Order.OrderType.MARKETORDER, Order.OfferType.SELL, shareId);

            buyMaxPrice = orderRepository.findAllByStateAndOfferTypeAndOrderTypeAndShareIdOrderByTimestamp(Order.State.OPEN, Order.OrderType.LIMITORDER, Order.OfferType.BUY, shareId);

            sellMinPrice =orderRepository.findAllByStateAndOfferTypeAndOrderTypeAndShareIdOrderByTimestamp(Order.State.OPEN, Order.OrderType.STOPORDER, Order.OfferType.SELL, shareId);



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
        //}





        //TODO den einkommenden request richtig einordnen

        /*if (request.getOfferType() == Order.OfferType.BUY) {
            buyOrders.add(request);
        }
        if (request.getOfferType()== Order.OfferType.SELL){
            sellOrders.add(request);
        }

         */



        matchinAlgo(shareId, buyMarket, sellMarket, buyMaxPrice, sellMinPrice);


        return request;
    }


    public void matchinAlgo(long shareId, ArrayList<Order> buyMarketList, ArrayList<Order> sellMarketList, ArrayList<Order> buyMaxPriceList, ArrayList<Order> sellMinPriceList) throws Exception{
        double referenzpreis = shareRepository.getShareById(shareId).getPrice();

        if(sellMarketList!=null){
            long sellOrderId = sellMarketList.get(0).getId();
            if (buyMarketList!=null){
                long buyOrderId = buyMarketList.get(0).getId();
                buyMarketList.remove(0);
                sellMarketList.remove(0);
                executeOrder(shareId, sellOrderId,buyOrderId);

                //ruft sich selber wieder auf um zu prüfen ob weitere Matches möglich sind
                matchinAlgo(shareId, buyMarketList, sellMarketList, buyMaxPriceList, sellMinPriceList);
            }
            else if(buyMaxPriceList!=null){
                double highestbid=0.0;
                Order bestBuyOrder=null;
                for (Order order : buyMaxPriceList) {
                    if (order.getMaxMinPreis() > highestbid) {
                        highestbid = order.getMaxMinPreis();
                        bestBuyOrder= order;
                    }
                }
                sellMarketList.remove(0); //Orders aus buch löschen
                buyMaxPriceList.remove(bestBuyOrder);

                //Verkäufer verkauft zu jedem Preis und daher wird er mit dem Käufer gematcht der das meiste bietet.
                executeOrder(highestbid, sellOrderId,bestBuyOrder.getId());
                matchinAlgo(shareId, buyMarketList, sellMarketList, buyMaxPriceList, sellMinPriceList); //matching Algo erneut anstoßen falls durch neuen refP neue Matches möglich sind

            } //elseif
        }//if

        else if(!buyMarketList.isEmpty()){
            long buyOrderId = buyMarketList.get(0).getId();
            if(sellMinPriceList!=null){
                double lowestAcceptablePrice =sellMinPriceList.get(0).getMaxMinPreis();
                Order sellOrder =sellMinPriceList.get(0);
                for (Order order: sellMinPriceList){
                    if(order.getMaxMinPreis()<lowestAcceptablePrice){
                        lowestAcceptablePrice=order.getMaxMinPreis();
                        sellOrder=order;
                    }
                }
                buyMarketList.remove(0);
                sellMinPriceList.remove(sellOrder);

                executeOrder(lowestAcceptablePrice,  sellOrder.getId(),buyOrderId);

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
                    executeOrder(highestBid,  bestSellOrder.getId(),bestBuyOrder.getId());

                }else{
                    executeOrder(referenzpreis,  bestSellOrder.getId(),bestBuyOrder.getId());
                }
                matchinAlgo(shareId, buyMarketList, sellMarketList, buyMaxPriceList, sellMinPriceList);
            }
        }


    }




    public void executeOrder(double refP, long sellOrderId, long buyOrderId) throws Exception{
        //TODO geld abbuchen Aktein transferieren
        //geld vom depot abbuchen
        //aktien vom Verkäufer abziehen und dem Käufer gutschreiben
        //Orders auf closed setzten

        Order sellOrder = orderRepository.getOrderById(sellOrderId);
        sellOrder.setState(Order.State.CLOSED);

        Depot sellDepot = depotRepository.getById(sellOrder.getDepotId());
        sellDepot.setTotalCash(sellDepot.getTotalCash()+(sellOrder.getCount()*refP));

        Share shareToSell = shareRepository.getShareById(sellOrder.getShareId());
        DepotShareAmount sellDepotShareAmount = depotShareAmountRepository.findDepotShareAmountByDepotAndShare(sellDepot, shareToSell);
        sellDepotShareAmount.setAmount(sellDepotShareAmount.getAmount()-sellOrder.getCount());
        if (sellDepotShareAmount.getAmount()<0){
            throw new Exception("negative Share amount");
        }




        Order buyOrder= orderRepository.getOrderById(buyOrderId);
        buyOrder.setState(Order.State.CLOSED);

        Depot buyDepot = depotRepository.getById(buyOrder.getDepotId());
        buyDepot.setTotalCash(buyDepot.getTotalCash()-(buyOrder.getCount()*refP));
        if(buyDepot.getTotalCash()<0){
            throw  new Exception("nicht genug geld um die Aktien zu kaufen");
        }

        // depot share amout suchen und hochzählen wenn nciht vorhanden neu anlegen
        DepotShareAmount buyDepotShareAmount = depotShareAmountRepository.findDepotShareAmountByDepotAndShare(buyDepot, shareToSell);
        if(buyDepotShareAmount==null){
            buyDepotShareAmount = new DepotShareAmount(shareToSell, buyDepot, 0);
        }
        buyDepotShareAmount.setAmount(buyOrder.getCount());


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
