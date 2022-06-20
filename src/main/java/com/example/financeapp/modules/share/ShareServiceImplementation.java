package com.example.financeapp.modules.share;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShareServiceImplementation implements ShareService {

    private final ShareRepository shareRepository;

    @Autowired
    public ShareServiceImplementation(ShareRepository shareRepository){
        this.shareRepository= shareRepository;
    }


    @Override
    public List<Share> findAllShare() {
        return shareRepository.findAll();
    }

    @Override
    public List getSharePrices() throws Exception {
        List<Share> shares = new ArrayList<Share>();
        List<Double> prices = new ArrayList<Double>();
        shares = shareRepository.findAll();
        for(int i=0; i < shares.size(); i++) {
            double price = shares.get(i).getPrice();
            prices.add(price);
        }
        return prices;
    }

    @Override
    public List getSharePrice(Long id) throws Exception {
        List<Share> shares = new ArrayList<Share>();
        List<Double> prices = new ArrayList<Double>();
        Share share = shareRepository.getShareById(id);
        prices.add(share.getPrice());
        return prices;
    }

    @Override
    public List<Share> findAllShareByCategory(String category) throws Exception {
        List<Share> shares = new ArrayList<Share>();
        shares = shareRepository.findAllByCategory(category);

        return shares;
    }

    @Override
    public void deleteOrder(Long id) throws Exception {
        try{
            Share share = shareRepository.getShareById(id);
            shareRepository.delete(share);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
