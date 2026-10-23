
package com.octafinDesafio.mock_banks.service;

import com.octafinDesafio.mock_banks.dto.AuroraRequest;
import com.octafinDesafio.mock_banks.dto.AuroraResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class AuroraService {

    public AuroraResponse simulate(AuroraRequest request) {

        if (request.salary() == null || request.loanAmount() == null
                || request.installments() == null
                || request.salary().signum() <= 0
                || request.loanAmount().signum() <= 0
                || request.installments() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Dados inválidos"
            );
        }

        BigDecimal rate = new BigDecimal("0.018");
        int installments = request.installments();

        BigDecimal total = request.loanAmount()
                .multiply(BigDecimal.ONE.add(
                        rate.multiply(BigDecimal.valueOf(installments))
                ));

        BigDecimal installmentValue = total.divide(
                BigDecimal.valueOf(installments),
                2,
                RoundingMode.HALF_UP
        );

        BigDecimal salaryLimit = request.salary()
                .multiply(new BigDecimal("0.30"));

        if (installments > 48 || installmentValue.compareTo(salaryLimit) > 0) {
            return new AuroraResponse(false, null, null);
        }

        return new AuroraResponse(
                true,
                new BigDecimal("1.8"),
                installmentValue
        );
    }
}
