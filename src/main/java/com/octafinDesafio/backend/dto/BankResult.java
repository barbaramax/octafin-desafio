
package com.octafinDesafio.backend.dto;

import com.octafinDesafio.backend.enums.BankStatus;
import java.math.BigDecimal;

public record BankResult(
        String bankName,
        BankStatus status,
        BigDecimal monthlyInterestRate,
        BigDecimal installmentAmount,
        BigDecimal totalAmount
) {
}
