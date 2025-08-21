package com.letstrade.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.letstrade.model.User;
import com.letstrade.model.Wallet;
import com.letstrade.model.Withdrawal;
import com.letstrade.service.UserService;
import com.letstrade.service.WalletService;
import com.letstrade.service.WithdrawalService;


@RestController
@RequestMapping("/api/withdrawal")
public class WithdrawalController {

    @Autowired
    private WithdrawalService withdrawalService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @PostMapping("/{amount}")
    public ResponseEntity<?> withdrawalRequest(@PathVariable Long amount, @RequestHeader("Authorization") String jwt) throws Exception{

        User user = userService.findUserProfileByJwt(jwt);

        Wallet userWallet = walletService.getUserWallet(user);

        Withdrawal withdrawal = withdrawalService.requestWithdrawal(amount, user);

        walletService.addBalance(userWallet, -withdrawal.getAmount(), null);

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @PatchMapping("/api/admin/withdrawal/{id}/proceed/{accept}")
    public ResponseEntity<?> proceedWithdrawal(
        @PathVariable Long id,
        @PathVariable boolean accept,
        @RequestHeader("Authorization")String jwt)throws Exception{

            User user = userService.findUserProfileByJwt(jwt);

            Withdrawal withdrawal = withdrawalService.proceedWithWithdrawal(id, accept);

            Wallet userWallet = walletService.getUserWallet(user);

            if(!accept){
                walletService.addBalance(userWallet, withdrawal.getAmount(), null);
            }

            return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Withdrawal>> getWithdrawalHistory(@RequestHeader("Authorization")String jwt) throws Exception{

        User user = userService.findUserProfileByJwt(jwt);

        List<Withdrawal> withdrawal = withdrawalService.getUsersWithdrawalHistory(user);

        return new ResponseEntity<>(withdrawal,HttpStatus.OK);
    }
}
