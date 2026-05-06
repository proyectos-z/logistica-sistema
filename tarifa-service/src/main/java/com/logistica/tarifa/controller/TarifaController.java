package com.logistica.tarifa.controller;

import com.logistica.tarifa.model.Tarifa;
import com.logistica.tarifa.service.TarifaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tarifas")
public class TarifaController {

    private final TarifaService tarifaService;

    public TarifaController(TarifaService tarifaService) {
        this.tarifaService = tarifaService;
    }

    // Consulta sincrona desde envio-service (Orquestacion)
    @GetMapping("/calcular")
    public ResponseEntity<Tarifa> calcular(
            @RequestParam("envioId") String envioId,
            @RequestParam("destino") String destino,
            @RequestParam("peso") double peso) {

        Tarifa tarifa = tarifaService.calcular(envioId, destino, peso);
        return ResponseEntity.ok(tarifa);
    }
}
