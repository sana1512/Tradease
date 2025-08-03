package com.letstrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.letstrade.model.VerificationCode;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long>{

    public VerificationCode findByUserId(Long userId);
}
