package com.example.financeapp.modules.depot;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DepotShareAmountRepository extends JpaRepository<DepotShareAmount, Long> {
    List<Long> findAllByDepot(Depot depot);
}