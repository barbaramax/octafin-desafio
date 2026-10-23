
package com.octafinDesafio.mock_banks.dto;

import java.math.BigDecimal;

public record AuroraResponse(
        Boolean approved,
        BigDecimal interestRate,
        BigDecimal installmentValue
) {
}
