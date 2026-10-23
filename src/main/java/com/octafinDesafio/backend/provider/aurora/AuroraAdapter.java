
package com.octafinDesafio.backend.provider.aurora;

import com.octafinDesafio.backend.dto.BankResult;
import com.octafinDesafio.backend.dto.SimulationRequest;
import com.octafinDesafio.backend.enums.BankStatus;
import com.octafinDesafio.backend.provider.BankProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;

@Component
public class AuroraAdapter implements BankProvider {

    private final RestClient restClient;

    public AuroraAdapter(@Value("${banks.aurora.url}") String url) {
        this.restClient = RestClient.create(url);
    }

    @Override
    public BankResult simulate(SimulationRequest request) {
        try {
            AuroraRequest data = new AuroraRequest(
                    request.salary(),
                    request.loanAmount(),
                    request.installments()
            );

            AuroraResponse response = restClient.post()
                    .uri("/banks/aurora/simulate")
                    .body(data)
                    .retrieve()
                    .body(AuroraResponse.class);

            if (response == null || response.approved() == null) {
                return new BankResult("Aurora", BankStatus.UNAVAILABLE, null, null, null);
            }

            if (!response.approved()) {
                return new BankResult("Aurora", BankStatus.REJECTED, null, null, null);
            }

            if (response.interestRate() == null || response.installmentValue() == null) {
                return new BankResult("Aurora", BankStatus.UNAVAILABLE, null, null, null);
            }

            BigDecimal total = response.installmentValue()
                    .multiply(BigDecimal.valueOf(request.installments()));

            return new BankResult(
                    "Aurora",
                    BankStatus.APPROVED,
                    response.interestRate(),
                    response.installmentValue(),
                    total
            );

        } catch (RestClientException e) {
            return new BankResult("Aurora", BankStatus.UNAVAILABLE, null, null, null);
        }
    }

    private record AuroraRequest(
            BigDecimal salary,
            BigDecimal loanAmount,
            Integer installments
    ) {}

    private record AuroraResponse(
            Boolean approved,
            BigDecimal interestRate,
            BigDecimal installmentValue
    ) {}
}
