package com.letstrade.request;

import com.letstrade.domain.VerificationType;

import lombok.Data;

@Data
public class ForgotPasswordTokenRequest {

    private String sendTo;
    private VerificationType verificationType;
}
