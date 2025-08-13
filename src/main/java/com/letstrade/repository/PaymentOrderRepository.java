package com.letstrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.letstrade.model.PaymentOrder;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder,Long>{


}
