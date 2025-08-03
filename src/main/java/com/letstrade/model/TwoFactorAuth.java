package com.letstrade.model;

import com.letstrade.domain.VerificationType;

import lombok.Data;

@Data
public class TwoFactorAuth {

    private boolean isEnabled = false;
    private VerificationType sendTo;
}
