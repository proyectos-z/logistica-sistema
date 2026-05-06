package com.logistica.notificacion.controller;

import com.logistica.notificacion.model.Notificacion;
import com.logistica.notificacion.service.NotificacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    // Historial persistido en BD propia (notificacion_db)
    @GetMapping
    public ResponseEntity<List<Notificacion>> obtenerHistorial() {
        return ResponseEntity.ok(notificacionService.obtenerHistorial());
    }
}
