package com.letstrade.service;

import com.letstrade.model.Order;
import com.letstrade.model.User;
import com.letstrade.model.Wallet;

public interface WalletService {

    Wallet getUserWallet(User user);

    Wallet addBalance(Wallet wallet, Long money, Long orderId);

    Wallet findWalletById(Long id)throws Exception;

    Wallet walletToWalletTransfer(User send, Wallet receiverWallet, double amount, String purpose)throws Exception;

    Wallet payOrderPayment(Order order, User user)throws Exception;
}
