package com.example.financeapp.modules.depot;

import java.util.List;
import com.example.financeapp.modules.depot.DepoShareAmount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepoShareAmountRepository extends JpaRepository<DepoShareAmount, Long> {
    List<Long> findAllByDepot(Depot depot);
}