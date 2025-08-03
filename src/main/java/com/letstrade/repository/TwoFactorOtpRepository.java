package com.letstrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.letstrade.model.TwoFactorOTP;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOTP, String>{

    TwoFactorOTP findByUserId(Long userId);
}
