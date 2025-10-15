package com.negocio.proyecto;

// Importaciones necesarias
import Models.Deudor;
import Models.DeudorService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // Importa todas las anotaciones de Spring

import java.util.List;
// Ya no necesitas importar java.util.ArrayList si solo usas List

@RestController
@RequestMapping("/deudores")
@CrossOrigin(origins = "*")
public class Deudorcontroller {

    // 1. Declaración del servicio como final
    // Spring inyectará la ÚNICA instancia de DeudorService (@Service) aquí.
    private final DeudorService deudorService;

    // 2. Constructor para Inyección de Dependencias
    // Spring sabe qué instancia de DeudorService debe pasar aquí.
    public Deudorcontroller(DeudorService deudorService) {
        this.deudorService = deudorService;
    }

    // 📌 Alta de un deudor
    @PostMapping("/alta")
    public ResponseEntity<String> altaDeudor(@RequestBody Deudor nuevoDeudor) {
        // La lógica de negocio la maneja el Service
        boolean exito = deudorService.altaDeudor(nuevoDeudor);

        if (exito)
            return ResponseEntity.ok("✅ Deudor agregado correctamente.");
        else
            return ResponseEntity.badRequest().body("⚠️ El deudor ya existe.");
    }

    // 📌 Baja (lógica) de un deudor por DNI
    @DeleteMapping("/baja/{dni}")
    public ResponseEntity<String> bajaDeudor(@PathVariable String dni) {
        boolean exito = deudorService.bajaDeudor(dni);

        if (exito)
            return ResponseEntity.ok("🗑️ Deudor dado de baja correctamente.");
        else
            return ResponseEntity.notFound().build();
    }

    // 📌 Buscar un deudor por DNI
    @GetMapping("/{dni}")
    public ResponseEntity<Deudor> getDeudor(@PathVariable String dni) {
        Deudor d = deudorService.buscarDeudor(dni);

        if (d != null)
            return ResponseEntity.ok(d);
        else
            return ResponseEntity.notFound().build();
    }

    // 📌 Listar todos los deudores
    @GetMapping("/listar")
    public ResponseEntity<List<Deudor>> listarDeudores() {
        // Esto devolverá la lista con los datos de prueba cargados por el constructor del servicio
        return ResponseEntity.ok(deudorService.listarActivos());
    }
}