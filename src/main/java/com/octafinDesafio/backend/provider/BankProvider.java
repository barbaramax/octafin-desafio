
package com.octafinDesafio.backend.provider;

import com.octafinDesafio.backend.dto.BankResult;
import com.octafinDesafio.backend.dto.SimulationRequest;

public interface BankProvider {

    BankResult simulate(SimulationRequest request);

}
