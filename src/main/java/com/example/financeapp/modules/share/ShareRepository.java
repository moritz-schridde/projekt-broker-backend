package com.example.financeapp.modules.share;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShareRepository extends JpaRepository<Share, Long> {
    Share getShareById(Long id);
    List<Share> findAllByCategory(String category);
}
