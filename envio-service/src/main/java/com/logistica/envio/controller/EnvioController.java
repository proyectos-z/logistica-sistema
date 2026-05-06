package com.logistica.envio.controller;

import com.logistica.envio.model.Envio;
import com.logistica.envio.service.EnvioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {

    private final EnvioService envioService;

    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    @PostMapping
    public ResponseEntity<Envio> crearEnvio(@RequestBody Envio envio) {
        Envio resultado = envioService.procesarEnvio(envio);
        return ResponseEntity.ok(resultado);
    }
}
