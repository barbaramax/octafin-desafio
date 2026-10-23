
package com.octafinDesafio.mock_banks.dto;

import java.math.BigDecimal;

public record HorizonteRequest(
        BigDecimal salary,
        BigDecimal loanAmount,
        Integer installments
) {
}
