package com.letstrade.service;

import com.letstrade.model.PaymentDetails;
import com.letstrade.model.User;

public interface PaymentDetailsService {

    public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, 
                                            String ifsc, String bankName, User user);

    public PaymentDetails getUsersPaymentDetails(User user);
}
