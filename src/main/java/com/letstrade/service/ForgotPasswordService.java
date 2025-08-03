package com.letstrade.service;

import com.letstrade.domain.VerificationType;
import com.letstrade.model.ForgotPasswordToken;
import com.letstrade.model.User;

public interface ForgotPasswordService {

    ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType,
                                    String sendTo);

    ForgotPasswordToken findById(String id);

    ForgotPasswordToken findByUser(Long userId);

    void deleteToken(ForgotPasswordToken token);
}
