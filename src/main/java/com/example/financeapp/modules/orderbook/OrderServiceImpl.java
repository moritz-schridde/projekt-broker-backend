package com.example.financeapp.modules.orderbook;

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


    @Override
    public List<Order> findAllOrders() {
        List<Order> orders =orderRepository.findAll();
        List<Order> orderResponse = new ArrayList<>();
        orders.forEach(order -> orderResponse.add(order));
        return orderResponse;
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


            buyMarket = orderRepository.findAllByStateAndOfferTypeAndOrderTypeOrderByTimestamp(Order.State.OPEN, Order.OrderType.MARKETORDER, Order.OfferType.BUY);

            sellMarket= orderRepository.findAllByStateAndOfferTypeAndOrderTypeOrderByTimestamp(Order.State.OPEN, Order.OrderType.MARKETORDER, Order.OfferType.SELL);


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


        if (request.getOfferType() == Order.OfferType.BUY) {
            buyOrders.add(request);
        }
        if (request.getOfferType()== Order.OfferType.SELL){
            sellOrders.add(request);
        }





        return request;
    }


    public void matchinAlgo(double refP, ArrayList<Order> buyMarketList, ArrayList<Order> sellMarketList, ArrayList<Order> buyMaxPriceList, ArrayList<Order> sellMinPriceList){
        if(sellMarketList!=null){
            long firstOrderId = sellMarketList.get(0).getId();
            if (buyMarketList!=null){
                long secondOrderId = buyMarketList.get(0).getId();
                buyMarketList.remove(0);
                sellMarketList.remove(0);
                executeOrder(refP, firstOrderId,secondOrderId);
                matchinAlgo(refP, buyMarketList, sellMarketList, buyMaxPriceList, sellMinPriceList);
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

                executeOrder(highestbid, bestOrder.getId(), firstOrderId);
                matchinAlgo(highestbid, buyMarketList, sellMarketList, buyMaxPriceList, sellMinPriceList); //matching Algo erneut anstoßen falls durch neuen refP neue Matches möglich sind

            }

        }
    }

    public void executeOrder(double refP, long firstOrderId, long secondOrderId){
        //geld vom depot abbuchen
        //aktien vom Verkäufer abziehen und dem Käufer gutschreiben
    }


    public void SetNewSharePrice(long shareId, double price){

    }



}
