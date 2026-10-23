
package com.octafinDesafio.mock_banks.controller;

import com.octafinDesafio.mock_banks.dto.AuroraRequest;
import com.octafinDesafio.mock_banks.dto.AuroraResponse;
import com.octafinDesafio.mock_banks.service.AuroraService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/banks/aurora")
public class AuroraController {

    private final AuroraService service;

    public AuroraController(AuroraService service) {
        this.service = service;
    }

    @PostMapping("/simulate")
    @ResponseStatus(HttpStatus.OK)
    public AuroraResponse simulate(@RequestBody AuroraRequest request) {
        return service.simulate(request);
    }
}
