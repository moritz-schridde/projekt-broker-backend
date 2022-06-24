package com.example.financeapp.modules.share;

import java.util.List;

public interface ShareService {
    List<Share> findAllShare() throws Exception;
    Share findShare(Long id) throws Exception;
    List getSharePrices() throws Exception;
    List getSharePrice(Long id) throws Exception;
    List<Share> findAllShareByCategory(String category) throws Exception;

    void postShare(String name, String shortname, double price, String category, byte iconId, String wkn) throws Exception;

    void deleteOrder(Long id) throws Exception;
}
