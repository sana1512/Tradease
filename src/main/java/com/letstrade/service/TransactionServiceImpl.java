package com.letstrade.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letstrade.model.Wallet;
import com.letstrade.model.WalletTransaction;
import com.letstrade.repository.WalletTransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private WalletTransactionRepository walletTransactionRepository;
    
    @Override
    public List<WalletTransaction> getTransactionByWallet(Wallet wallet) {
        
        return walletTransactionRepository.findByWallet(wallet);
    }
}
