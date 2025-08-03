package com.letstrade.service;

import java.util.List;

import com.letstrade.domain.OrderType;
import com.letstrade.model.Coin;
import com.letstrade.model.Order;
import com.letstrade.model.OrderItem;
import com.letstrade.model.User;

public interface OrderService {

    Order createOrder(User user, OrderItem orderItem, OrderType orderType);

    Order getOrderById(Long orderId)throws Exception;

    List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol);

    Order processOrder(Coin coin, double quantity, OrderType orderType, User user)throws Exception;

    
}
