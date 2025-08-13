package com.letstrade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.letstrade.domain.PaymentMethod;
import com.letstrade.model.PaymentOrder;
import com.letstrade.model.User;
import com.letstrade.response.PaymentResponse;
import com.letstrade.service.PaymentService;
import com.letstrade.service.UserService;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/api/payment/{paymentMethod}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentHandler(
                @PathVariable PaymentMethod paymentMethod,
                @PathVariable Long amount,
                @RequestHeader("Authorization") String jwt)throws Exception, RazorpayException, StripeException{

        User user = userService.findUserProfileByJwt(jwt);

        PaymentResponse paymentResponse;

        PaymentOrder order = paymentService.createOrder(user, amount, paymentMethod);

        paymentResponse = paymentService.createStripePaymentLink(user, amount, order.getId());

        return new ResponseEntity<>(paymentResponse,HttpStatus.CREATED);
    }

}
