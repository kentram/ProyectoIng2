package com.negocio.proyecto.controller;

import com.negocio.proyecto.Models.Item;
import com.negocio.proyecto.config.MercadoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @PostMapping("/crear-pago")  // <-- Ruta completa: /productos/crear-pago
    public ResponseEntity<?> crearPago(@RequestBody List<Item> items) {
        try {
            System.out.println("Recibiendo petición POST en /productos/crear-pago");
            System.out.println("Items recibidos: " + items);

            String prefId = mercadoPagoService.crearPreferenciaPago(items);

            System.out.println("Preferencia creada con ID: " + prefId);

            return ResponseEntity.ok(Map.of("preferenceId", prefId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al crear preferencia: " + e.getMessage()));
        }
    }

}
