package com.letstrade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.letstrade.model.Order;
import com.letstrade.model.User;
import com.letstrade.model.Wallet;
import com.letstrade.model.WalletTransaction;
import com.letstrade.service.OrderService;
import com.letstrade.service.UserService;
import com.letstrade.service.WalletService;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/api/wallet")
    public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt){

        User user = userService.findUserProfileByJwt(jwt);
        Wallet wallet = walletService.getUserWallet(user);

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("/api/wallet/{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(@RequestHeader("Authorization") String jwt,
                                                        @PathVariable Long walletId,
                                                        @RequestBody WalletTransaction req)throws Exception{

        User senderUser = userService.findUserProfileByJwt(jwt);
        Wallet receiverWallet = walletService.findWalletById(walletId);
        Wallet wallet = walletService.walletToWalletTransfer(senderUser, receiverWallet, req.getAmount());
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }
    
    @PutMapping("/api/wallet/order/{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(@RequestHeader("Authorization") String jwt,
                                                        @PathVariable Long orderId)throws Exception{

        User user = userService.findUserProfileByJwt(jwt);

        Order order = orderService.getOrderById(orderId);

        Wallet wallet = walletService.payOrderPayment(order, user);
        
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }
}