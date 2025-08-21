package com.letstrade.service;

import com.letstrade.domain.PaymentMethod;
import com.letstrade.model.PaymentOrder;
import com.letstrade.model.User;
import com.letstrade.response.PaymentResponse;
import com.stripe.exception.StripeException;

public interface PaymentService {

    PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod);

    PaymentOrder getPaymentOrderById(Long id)throws Exception;

    Boolean proceedPaymentOrder(PaymentOrder paymentOrder);

    PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId)throws StripeException;
}