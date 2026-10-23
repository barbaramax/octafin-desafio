
package com.octafinDesafio.mock_banks.dto;

import java.math.BigDecimal;

public record AuroraRequest(
        BigDecimal salary,
        BigDecimal loanAmount,
        Integer installments
) {
}
