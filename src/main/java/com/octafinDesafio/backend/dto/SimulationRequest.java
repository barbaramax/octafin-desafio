
package com.octafinDesafio.backend.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.constraints.br.CNPJ;

public record SimulationRequest(

        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @CPF(message = "CPF inválido. Verifique os números informados.")
        String cpf,

        @NotNull
        @DecimalMin("0.01")
        BigDecimal salary,

        @NotBlank
        @CNPJ(message = "CNPJ inválido. Verifique os números informados.")
        String employerCnpj,

        @NotNull
        @DecimalMin("0.01")
        BigDecimal loanAmount,

        @NotNull
        @Min(1)
        Integer installments

) {}
