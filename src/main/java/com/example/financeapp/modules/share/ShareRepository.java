package com.example.financeapp.modules.share;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareRepository extends JpaRepository<Share, Long> {
    Share getShareById(Long id);
}
