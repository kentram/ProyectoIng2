package com.negocio.proyecto;

import ConnectionsDataBase.QueriesDataBase;
import Models.Item;
import Models.Producto;
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

/*    @PostMapping("/enviarLista")
    public ResponseEntity<String> recibirLista(@RequestBody List<Item> items) {
        listaItems.addAll(items);
        return ResponseEntity.ok("Lista recibida correctamente, tamaño: " + listaItems.size());
    }*/
@PostMapping("/enviarLista")
public ResponseEntity<String> recibirLista(@RequestBody List<Item> items) {  // de prueba:Daniel
    System.out.println("📦 Lista recibida:");
    for (Item item : items) {
        System.out.println("➡️ " + item);
    }

    listaItems.addAll(items);
    return ResponseEntity.ok("Lista recibida correctamente, tamaño: " + listaItems.size());
}

}

