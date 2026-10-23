
package com.octafinDesafio.backend.controller;

import com.octafinDesafio.backend.dto.BankResult;
import com.octafinDesafio.backend.dto.SimulationRequest;
import com.octafinDesafio.backend.service.SimulationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/simulations")
public class SimulationController {

    private final SimulationService service;

    public SimulationController(SimulationService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BankResult> simulate(@Valid @RequestBody SimulationRequest request) {
        return service.simulate(request);
    }
}
