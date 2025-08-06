package com.letstrade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letstrade.model.PaymentDetails;
import com.letstrade.model.User;
import com.letstrade.repository.PaymentDetailsRepository;

@Service
public class PaymentDetailsImpl implements PaymentDetailsService{

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;


    @Override
    public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifsc,
            String bankName, User user) {
        
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setAccountNumber(accountNumber);
        paymentDetails.setAccountHolderName(accountHolderName);
        paymentDetails.setIfsc(ifsc);
        paymentDetails.setBankName(bankName);
        paymentDetails.setUser(user);

        return paymentDetailsRepository.save(paymentDetails);
    }

    @Override
    public PaymentDetails getUsersPaymentDetails(User user) {
        
        return paymentDetailsRepository.findByUserId(user.getId());
    }

}
