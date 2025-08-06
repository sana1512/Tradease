package com.letstrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.letstrade.model.Watchlist;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long>{

    Watchlist findByUserId(Long userId);
    
}
