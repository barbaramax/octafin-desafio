
package com.octafinDesafio.mock_banks.dto;

import java.math.BigDecimal;

public record HorizonteResponse(
        String status,
        BigDecimal monthlyRate,
        BigDecimal monthlyPayment
) {
}
