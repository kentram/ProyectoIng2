package com.negocio.proyecto.controller;

// Importaciones necesarias
import com.negocio.proyecto.DAO.IDeudorDAO;
import com.negocio.proyecto.Models.Deudor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // Importa todas las anotaciones de Spring

import java.util.List;
// Ya no necesitas importar java.util.ArrayList si solo usas List

@RestController
@RequestMapping("/deudores")
@CrossOrigin(origins = "*")
public class DeudorController {


    // 1. Declaración del servicio como final
    // Spring inyectará la ÚNICA instancia de DeudorService (@Service) aquí.
    private final IDeudorDAO deudorDAO;

    // 2. Constructor para Inyección de Dependencias
    // Spring sabe qué instancia de DeudorService debe pasar aquí.
    @Autowired
    public DeudorController(IDeudorDAO deudorDAO) {
        this.deudorDAO = deudorDAO;
    }

    // Alta de un deudor
    @PostMapping("/alta")
    public ResponseEntity<String> altaDeudor(@RequestBody Deudor nuevoDeudor) {
        // La lógica de negocio la maneja el Service
        boolean res = deudorDAO.registrarDeudor(nuevoDeudor);
        if (res)
            return ResponseEntity.ok("✅ Deudor agregado correctamente.");
        else
            return ResponseEntity.badRequest().body("El deudor ya existe.");
    }

    // Baja (lógica) de un deudor por DNI
    @DeleteMapping("/baja/{dni}")
    public ResponseEntity<String> bajaDeudor(@PathVariable int dni) {
        boolean res = deudorDAO.eliminarDeudor(dni);
        if (res)
            return ResponseEntity.ok(" Deudor dado de baja correctamente.");
        else
            return ResponseEntity.notFound().build();
    }

    // Buscar un deudor por DNI
    @GetMapping("/{dni}")
    public ResponseEntity<Deudor> getDeudor(@PathVariable int dni) {
        Deudor deudor = deudorDAO.buscarDeudor(dni);
        if (deudor != null)
            return ResponseEntity.ok(deudor);
        else
            return ResponseEntity.notFound().build();
    }


    // Listar todos los deudores
    @GetMapping("/listar")
    public ResponseEntity<List<Deudor>> listarDeudores() {
        // Esto devolverá la lista con los datos de prueba cargados por el constructor del servicio
        List<Deudor> lista = deudorDAO.listarDeudores();
        if(lista != null)
            return ResponseEntity.ok(lista);
        else
            return ResponseEntity.notFound().build();
    }

}