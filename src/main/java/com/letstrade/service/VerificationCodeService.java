package com.letstrade.service;

import com.letstrade.domain.VerificationType;
import com.letstrade.model.User;
import com.letstrade.model.VerificationCode;

public interface VerificationCodeService {
    VerificationCode sendVerificationCode(User user, VerificationType verificationType);

    VerificationCode getVerificationCodeById(Long id);

    VerificationCode getVerificationCodeByUser(Long userId);

    void deleteVerificationCodeById(VerificationCode verificationCode);
}
