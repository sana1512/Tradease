package com.letstrade.service;

import com.letstrade.model.Coin;
import com.letstrade.model.User;
import com.letstrade.model.Watchlist;

public interface WatchlistService {

    Watchlist findUserWatchlist(Long userId)throws Exception;

    Watchlist createWatchlist(User user);

    Watchlist findById(Long id)throws Exception;

    Coin addItemToWatchlist(Coin coin, User user)throws Exception;
}
