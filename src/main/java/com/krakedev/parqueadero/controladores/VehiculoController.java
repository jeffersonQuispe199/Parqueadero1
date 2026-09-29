package com.krakedev.parqueadero.controladores;

import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.krakedev.parqueadero.modelo.Auto;
import com.krakedev.parqueadero.modelo.Motocicleta;
import com.krakedev.parqueadero.modelo.Vehiculo;
import com.krakedev.parqueadero.servicios.ServicioVehiculo;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {

    private final ServicioVehiculo servicioVehiculos;

    public VehiculoController(ServicioVehiculo servicioVehiculos) {
        this.servicioVehiculos = servicioVehiculos;
    }

    @PostMapping("/auto")
    public ResponseEntity<String> ingresarAuto(@RequestBody Auto auto) {
        boolean exito = servicioVehiculos.ingresarVehiculo(auto);
        if (exito) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Auto ingresado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo ingresar: cupo lleno o placa duplicada.");
        }
    }

    @PostMapping("/moto")
    public ResponseEntity<String> ingresarMoto(@RequestBody Motocicleta moto) {
        boolean exito = servicioVehiculos.ingresarVehiculo(moto);
        if (exito) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Motocicleta ingresada correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo ingresar: cupo lleno o placa duplicada.");
        }
    }

    @GetMapping
    public ArrayList<Vehiculo> listarVehiculos() {
        return servicioVehiculos.listarVehiculos();
    }

    @GetMapping("/{placa}")
    public ResponseEntity<Vehiculo> buscarPorPlaca(@PathVariable String placa) {
        Vehiculo vehiculo = servicioVehiculos.buscarPorPlaca(placa);
        if (vehiculo != null) {
            return ResponseEntity.ok(vehiculo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}