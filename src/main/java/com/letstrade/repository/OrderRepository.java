package com.letstrade.repository;

import com.letstrade.model.Order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long>{

    List<Order> findByUserId(Long userId);
}
