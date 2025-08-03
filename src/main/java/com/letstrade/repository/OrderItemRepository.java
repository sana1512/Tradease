package com.letstrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.letstrade.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long>{

    
}
