package com.logistica.tarifa.service;

import com.logistica.tarifa.model.HistorialTarifa;
import com.logistica.tarifa.model.Tarifa;
import com.logistica.tarifa.repository.HistorialTarifaRepository;
import org.springframework.stereotype.Service;

@Service
public class TarifaService {

    private static final double TARIFA_BASE = 5.0;
    private static final double TARIFA_POR_KG = 2.5;
    private static final double RECARGO_INTERNACIONAL = 1.5;

    private final HistorialTarifaRepository historialRepository;

    public TarifaService(HistorialTarifaRepository historialRepository) {
        this.historialRepository = historialRepository;
    }

    public Tarifa calcular(String envioId, String destino, double peso) {
        double costo = TARIFA_BASE + (peso * TARIFA_POR_KG);

        if (esInternacional(destino)) {
            costo *= RECARGO_INTERNACIONAL;
        }

        double costoFinal = Math.round(costo * 100.0) / 100.0;

        // Persiste en BD propia (Database per Service) — aislamiento garantizado
        historialRepository.save(new HistorialTarifa(envioId, destino, peso, costoFinal));

        return new Tarifa(envioId, destino, peso, costoFinal);
    }

    private boolean esInternacional(String destino) {
        return destino != null && (destino.contains("-INT") || destino.startsWith("EX-"));
    }
}
