package com.negocio.proyecto.DAO;

import com.negocio.proyecto.Models.Producto;
import com.negocio.proyecto.Models.Categoria;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//Contiene la lógica SQL
@Repository
public class ProductoDAOImpl implements IProductoDAO {

    private final JdbcTemplate jdbcTemplate;

    public ProductoDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Producto buscarPorCodigo(String code) {
        String sql = "SELECT p.id_producto, p.nombre, p.precio, p.sector, " +
                "c.codigo AS barcode, l.cantidad " +
                "FROM producto p " +
                "JOIN codigo_barra c ON p.id_producto = c.id_producto " +
                "JOIN listado l ON c.id = l.id_codigo " +
                "WHERE c.codigo = ?";

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{code},
                    (rs, rowNum) -> {
                        String barcode = rs.getString("barcode");
                        String name = rs.getString("nombre");
                        float cost = rs.getFloat("precio");
                        String categoriaStr = rs.getString("sector");
                        Categoria categoriaEnum = Categoria.valueOf(categoriaStr.toUpperCase());
                        return new Producto(barcode, name, cost, categoriaEnum);
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No se encontró ningún producto con el código " + code);
            return null;
        } catch (Exception e) {
            System.out.println("Error al buscar producto: " + e.getMessage());
            return null;
        }
    }
}
