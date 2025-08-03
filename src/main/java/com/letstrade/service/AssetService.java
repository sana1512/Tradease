package com.letstrade.service;

import java.util.List;

import com.letstrade.model.Asset;
import com.letstrade.model.Coin;
import com.letstrade.model.User;

public interface AssetService {

    Asset createAsset(User user, Coin coin, double quantity);

    Asset getAssetById(Long assetId)throws Exception;

    Asset getAssetByUserIdAndId(Long userId, Long assetId);
    
    List<Asset> getUsersAssets(Long userId);

    Asset updateAsset(Long assetId, double quantity)throws Exception;

    Asset findAssetByUserIdAndCoinId(Long userId, String coinId);

    void deleteAsset(Long assetId);
}
