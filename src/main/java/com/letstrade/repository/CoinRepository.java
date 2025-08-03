package com.letstrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.letstrade.model.Coin;

public interface CoinRepository extends JpaRepository<Coin, String>{

}
