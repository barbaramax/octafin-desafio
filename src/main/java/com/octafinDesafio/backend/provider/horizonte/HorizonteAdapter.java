
package com.octafinDesafio.backend.provider.horizonte;

import com.octafinDesafio.backend.dto.BankResult;
import com.octafinDesafio.backend.dto.SimulationRequest;
import com.octafinDesafio.backend.enums.BankStatus;
import com.octafinDesafio.backend.provider.BankProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import java.time.Duration;

import java.math.BigDecimal;

@Component
public class HorizonteAdapter implements BankProvider {

    private final RestClient restClient;

    public HorizonteAdapter(@Value("${banks.horizonte.url}") String url) {

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        factory.setConnectTimeout(Duration.ofSeconds(2));
        factory.setReadTimeout(Duration.ofSeconds(5));

        this.restClient = RestClient.builder()
                .baseUrl(url)
                .requestFactory(factory)
                .build();
    }

    @Override
    public BankResult simulate(SimulationRequest request) {
        try {
            HorizonteRequest data = new HorizonteRequest(
                    request.salary(),
                    request.loanAmount(),
                    request.installments()
            );

            HorizonteResponse response = restClient.post()
                    .uri("/banks/horizonte/simulate")
                    .body(data)
                    .retrieve()
                    .body(HorizonteResponse.class);

            if (response == null || response.status() == null) {
                return new BankResult("Horizonte", BankStatus.UNAVAILABLE, null, null, null);
            }

            if (response.status().equals("DENIED")) {
                return new BankResult("Horizonte", BankStatus.REJECTED, null, null, null);
            }

            if (!response.status().equals("ACCEPTED")
                    || response.monthlyRate() == null
                    || response.monthlyPayment() == null) {
                return new BankResult("Horizonte", BankStatus.UNAVAILABLE, null, null, null);
            }

            BigDecimal total = response.monthlyPayment()
                    .multiply(BigDecimal.valueOf(request.installments()));

            return new BankResult(
                    "Horizonte",
                    BankStatus.APPROVED,
                    response.monthlyRate(),
                    response.monthlyPayment(),
                    total
            );

        } catch (RestClientException e) {
            return new BankResult("Horizonte", BankStatus.UNAVAILABLE, null, null, null);
        }
    }

    private record HorizonteRequest(
            BigDecimal salary,
            BigDecimal loanAmount,
            Integer installments
    ) {}

    private record HorizonteResponse(
            String status,
            BigDecimal monthlyRate,
            BigDecimal monthlyPayment
    ) {}
}
