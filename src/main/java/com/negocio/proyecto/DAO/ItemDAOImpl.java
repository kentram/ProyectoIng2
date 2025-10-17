package com.negocio.proyecto.DAO;

import com.negocio.proyecto.Models.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemDAOImpl implements IItemDAO {
    private final JdbcTemplate jdbcTemplate;

    public ItemDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void registrarItems(List<Item> items, int id_venta){
        String sql = "INSERT INTO items_vendidos (id_venta, nombre, cantidad, precio_unitario, total) VALUES (?, ?, ?, ?, ?)";
        try {
            for (Item item : items) {
                float total = item.getCantidad() * item.getPrecio();

                jdbcTemplate.update(
                        sql,
                        id_venta,
                        item.getNombre(),
                        item.getCantidad(),
                        item.getPrecio(),
                        total
                );
            }

            System.out.println(" Items vinculados correctamente a la venta (ID " + id_venta + ")");
        } catch (Exception e) {
            System.err.println(" Error al registrar los ítems de la venta: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
