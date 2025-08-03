package com.letstrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.letstrade.model.ForgotPasswordToken;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken,String>{

    ForgotPasswordToken findByUserId(Long userId);
}
