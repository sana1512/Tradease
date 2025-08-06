package com.letstrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.letstrade.model.PaymentDetails;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails,Long>{

    PaymentDetails findByUserId(Long userId);
}
