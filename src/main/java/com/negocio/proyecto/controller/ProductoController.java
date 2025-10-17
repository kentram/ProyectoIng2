package com.negocio.proyecto.controller;

import com.negocio.proyecto.DAO.IProductoDAO;
import com.negocio.proyecto.DAO.IVentaDAO;
import com.negocio.proyecto.Models.Item;
import com.negocio.proyecto.Models.Producto;
import com.negocio.proyecto.Models.Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final List<Item> listaItems = new ArrayList<>();

    private final IProductoDAO productoDAO;
    private final IVentaDAO ventaDAO;

    @Autowired
    public ProductoController(IProductoDAO productoDAO, IVentaDAO ventaDAO) {
        this.productoDAO = productoDAO;
        this.ventaDAO = ventaDAO;
    }


    @GetMapping("/{codigo}")
    public ResponseEntity<Producto> getProducto(@PathVariable String codigo) {
        Producto p = productoDAO.buscarPorCodigo(codigo);
        if (p != null)
            return ResponseEntity.ok(p);
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/registrar-venta")
    public ResponseEntity<String> registrarVenta(@RequestBody Venta venta) {
        try {
            ventaDAO.registrarVenta(venta);
            return ResponseEntity.ok("Venta registrada correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al registrar la venta");
        }
    }


}

