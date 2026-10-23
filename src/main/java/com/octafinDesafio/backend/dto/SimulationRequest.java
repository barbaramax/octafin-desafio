
package com.octafinDesafio.backend.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record SimulationRequest(

        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String cpf,

        @NotNull
        @DecimalMin("0.01")
        BigDecimal salary,

        @NotBlank
        String employerCnpj,

        @NotNull
        @DecimalMin("0.01")
        BigDecimal loanAmount,

        @NotNull
        @Min(1)
        Integer installments

) {}
