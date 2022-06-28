package com.example.financeapp.modules.depot;

import java.util.List;

import com.example.financeapp.modules.share.Share;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepotShareAmountRepository extends JpaRepository<DepotShareAmount, Long> {
    List<Long> findAllByDepot(Depot depot);
    DepotShareAmount findDepotShareAmountByDepotAndShare(Depot depot, Share share);
}