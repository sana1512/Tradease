package com.letstrade.service;

import java.util.List;

import com.letstrade.model.Wallet;
import com.letstrade.model.WalletTransaction;

public interface TransactionService {

    List<WalletTransaction> getTransactionByWallet(Wallet wallet);
}
