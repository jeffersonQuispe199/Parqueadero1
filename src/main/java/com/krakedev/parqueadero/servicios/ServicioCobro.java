package com.krakedev.parqueadero.servicios;

import java.time.LocalDate;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import com.krakedev.parqueadero.modelo.TicketCobro;
import com.krakedev.parqueadero.modelo.Vehiculo;

@Service
public class ServicioCobro {

    private final ServicioVehiculo servicioVehiculos;
    private ArrayList<TicketCobro> historicoTickets = new ArrayList<>();

    public ServicioCobro(ServicioVehiculo servicioVehiculos) {
        this.servicioVehiculos = servicioVehiculos;
    }

    public TicketCobro procesarSalida(String placa, int horas) {
        Vehiculo vehiculo = servicioVehiculos.retirarVehiculo(placa);
        if (vehiculo == null) {
            return null;
        }

        // Invocación polimórfica directa (sin usar instanceof)
        double total = vehiculo.calcularTarifa(horas);

        String codigo = "TCK-" + (int) (Math.random() * 900 + 100);
        TicketCobro ticket = new TicketCobro(codigo, vehiculo, horas, total, LocalDate.now());
        historicoTickets.add(ticket);

        return ticket;
    }

    public double calcularTotalRecaudado() {
        double acumulado = 0.0;
        for (TicketCobro ticket : historicoTickets) {
            acumulado += ticket.getTotalPagar();
        }
        return acumulado;
    }

    public ArrayList<TicketCobro> listarTickets() {
        return historicoTickets;
    }
}