
package com.octafinDesafio.mock_banks.controller;

import com.octafinDesafio.mock_banks.dto.HorizonteRequest;
import com.octafinDesafio.mock_banks.dto.HorizonteResponse;
import com.octafinDesafio.mock_banks.service.HorizonteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/banks/horizonte")
public class HorizonteController {

    private final HorizonteService service;

    public HorizonteController(HorizonteService service) {
        this.service = service;
    }

    @PostMapping("/simulate")
    @ResponseStatus(HttpStatus.OK)
    public HorizonteResponse simulate(@RequestBody HorizonteRequest request) {
        return service.simulate(request);
    }
}
