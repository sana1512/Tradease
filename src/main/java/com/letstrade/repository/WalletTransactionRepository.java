package com.letstrade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.letstrade.model.Wallet;
import com.letstrade.model.WalletTransaction;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long>{

    List<WalletTransaction> findByWallet(Wallet wallet);
}
