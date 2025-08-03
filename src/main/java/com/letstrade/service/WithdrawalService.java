package com.letstrade.service;

import java.util.List;

import com.letstrade.model.User;
import com.letstrade.model.Withdrawal;

public interface WithdrawalService {

    Withdrawal requestWithdrawal(Long amount, User user);

    Withdrawal proceedWithWithdrawal(Long withdrawalId, boolean accept)throws Exception ;

    List<Withdrawal> getUsersWithdrawalHistory(User user);

    List<Withdrawal> getAllWithdrawalRequest();
}
