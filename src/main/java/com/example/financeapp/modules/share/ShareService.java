package com.example.financeapp.modules.share;

import java.util.List;

public interface ShareService {
    List<Share> findAllShare() throws Exception;
    List getSharePrices() throws Exception;
    List getSharePrice(Long id) throws Exception;
    void deleteOrder(Long id) throws Exception;
}
