package com.letstrade.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class WalletTransactionRequest {

    private BigDecimal amount;
    private String purpose;

}
