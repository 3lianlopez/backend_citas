package com.software.citas.service.state;

import com.software.citas.enums.EstadoCita;
import org.springframework.stereotype.Component;
@Component
public class CitaStateMachine {

    public EstadoCita confirmar(EstadoCita estadoActual) {

        if (estadoActual != EstadoCita.PENDIENTE) {
            throw new IllegalStateException(
                    "Solo una cita pendiente puede ser confirmada"
            );
        }

        return EstadoCita.CONFIRMADA;
    }

    public EstadoCita iniciar(EstadoCita estadoActual) {

        if (estadoActual != EstadoCita.CONFIRMADA) {
            throw new IllegalStateException(
                    "Solo una cita confirmada puede iniciarse"
            );
        }

        return EstadoCita.EN_PROCESO;
    }

    public EstadoCita finalizar(EstadoCita estadoActual) {

        if (estadoActual != EstadoCita.EN_PROCESO) {
            throw new IllegalStateException(
                    "Solo una cita en proceso puede finalizarse"
            );
        }

        return EstadoCita.FINALIZADA;
    }

    public EstadoCita cancelar(EstadoCita estadoActual) {

        if (estadoActual == EstadoCita.FINALIZADA ||
                estadoActual == EstadoCita.CANCELADA) {

            throw new IllegalStateException(
                    "La cita no puede ser cancelada"
            );
        }

        return EstadoCita.CANCELADA;
    }
}