package com.letstrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.letstrade.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long>{

    Wallet findByUserId(Long userId);
}
