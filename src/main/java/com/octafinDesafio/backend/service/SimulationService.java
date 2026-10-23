
package com.octafinDesafio.backend.service;

import com.octafinDesafio.backend.dto.BankResult;
import com.octafinDesafio.backend.dto.SimulationRequest;
import com.octafinDesafio.backend.provider.BankProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimulationService {

    private final List<BankProvider> banks;

    public SimulationService(List<BankProvider> banks) {
        this.banks = banks;
    }

    public List<BankResult> simulate(SimulationRequest request) {
        return banks.stream()
                .map(bank -> bank.simulate(request))
                .toList();
    }
}
