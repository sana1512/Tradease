package com.letstrade.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letstrade.domain.OrderType;
import com.letstrade.domain.WalletTransactionType;
import com.letstrade.model.Order;
import com.letstrade.model.User;
import com.letstrade.model.Wallet;
import com.letstrade.model.WalletTransaction;
import com.letstrade.repository.WalletRepository;
import com.letstrade.repository.WalletTransactionRepository;

@Service
public class WalletServiceImpl implements WalletService{

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    @Override
    public Wallet getUserWallet(User user) {

        Wallet wallet = walletRepository.findByUserId(user.getId());
        if(wallet==null){
            wallet = new Wallet();
            wallet.setUser(user);
            walletRepository.save(wallet);
        }
        return wallet;
    }

    @Override
    public Wallet addBalance(Wallet wallet, Long money, Long orderId) {

        WalletTransaction walletTransaction = new WalletTransaction();
        BigDecimal balance = wallet.getBalance();
        BigDecimal newBalance = balance.add(BigDecimal.valueOf(money));

        walletTransaction.setWallet(wallet);
        if(money>=0){
            walletTransaction.setType(WalletTransactionType.ADD_MONEY);
        }
        else{
            walletTransaction.setType(WalletTransactionType.WITHDRAWAL);
        }
        walletTransaction.setDate(LocalDateTime.now());
        walletTransaction.setTransferId(orderId);
        walletTransaction.setAmount(money);
        walletTransactionRepository.save(walletTransaction);

        wallet.setBalance(newBalance);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findWalletById(Long id) throws Exception {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if(wallet.isPresent()){
            return wallet.get();
        }
        throw new Exception("Wallet Not Found");
    }

    @Override
    public Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, double amount, String purpose) throws Exception {
        
        Wallet senderWallet = getUserWallet(sender);
        if(senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount))<0){
            throw new Exception("Insufficient Balance");
        }
        BigDecimal senderBalance = senderWallet.getBalance().subtract(BigDecimal.valueOf(amount));
        senderWallet.setBalance(senderBalance);
        walletRepository.save(senderWallet);

        BigDecimal receiverBalance = receiverWallet.getBalance().add(BigDecimal.valueOf(amount));
        receiverWallet.setBalance(receiverBalance);
        walletRepository.save(receiverWallet);

        WalletTransaction walletTransaction = new WalletTransaction();
        walletTransaction.setWallet(senderWallet);
        walletTransaction.setType(WalletTransactionType.WALLET_TRANSFER);
        walletTransaction.setDate(LocalDateTime.now());
        walletTransaction.setPurpose(purpose);
        walletTransaction.setAmount(amount);
        walletTransactionRepository.save(walletTransaction);
        return senderWallet;
    }

    @Override
    public Wallet payOrderPayment(Order order, User user) throws Exception {
        
        Wallet wallet = getUserWallet(user);

        if(order.getOrderType().equals(OrderType.BUY)){
            BigDecimal newBalance = wallet.getBalance().subtract(order.getPrice());
            if(newBalance.compareTo(order.getPrice())<0){
                throw new Exception("Insufficient funds for this transaction");
            }
            wallet.setBalance(newBalance);
        }
        else{
            BigDecimal newBalance = wallet.getBalance().add(order.getPrice());
            wallet.setBalance(newBalance);
        }
        walletRepository.save(wallet);
        return wallet;
    }

}
