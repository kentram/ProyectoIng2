package com.negocio.proyecto;

import ConnectionsDataBase.QueriesDataBase;
import Models.Item;
import Models.Producto;
import Models.Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final JdbcTemplate jdbcTemplate;
    private final List<Item> listaItems = new ArrayList<>();

    @Autowired
    public ProductoController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Producto> getProducto(@PathVariable String codigo) {
        Producto p = QueriesDataBase.productos(jdbcTemplate, codigo);
        if(p != null)
            return ResponseEntity.ok(p);
        else
            return ResponseEntity.notFound().build();
    }
    @PostMapping("/registrar-venta")
    public ResponseEntity<String> registrarVenta(@RequestBody Venta venta) {
        try {
            // Calcular total si no viene
            if (venta.getTotal() == 0 && venta.getItems() != null) {
                float total = 0;
                for (Item item : venta.getItems()) {
                    total += item.getPrecio() * item.getCantidad();
                }
                venta.setTotal(total);
            }

            if (venta.getFecha() == null)
                venta.setFecha(java.time.LocalDate.now());

            // 🔹 Mostrar en consola lo recibido
            System.out.println("=== VENTA RECIBIDA ===");
            System.out.println("Fecha: " + venta.getFecha());
            System.out.println("Total: $" + venta.getTotal());
            System.out.println("Pago: " +
                    (venta.getPago() != null ? venta.getPago().getMedioPago() : "No especificado"));
            System.out.println("Deudor: " +
                    (venta.getDeudor() != null ? venta.getDeudor() : "Sin deudor"));

            if (venta.getItems() != null) {
                System.out.println("Items recibidos:");
                for (Item item : venta.getItems()) {
                    System.out.println(" - Código: " + item.getCodigo()
                            + " | Nombre: " + item.getNombre()
                            + " | Categoría: " + item.getCategoria()
                            + " | Cantidad: " + item.getCantidad()
                            + " | Precio: $" + item.getPrecio());
                }
            }

            System.out.println("========================");

            QueriesDataBase.registrarVenta(jdbcTemplate, venta);

            return ResponseEntity.ok("Venta registrada correctamente");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al registrar la venta");
        }
    }



}

