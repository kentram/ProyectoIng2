package ConnectionsDataBase;

import Models.Categoria;
import Models.Producto;
import Models.Venta;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.Random;

public class QueriesDataBase {

    public static Producto productos(JdbcTemplate jdbcTemplate, String code) {
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
                        String barcode = rs.getString("barcode");  //no funciona por que el string debea ser del mismo tipo que defini
                        String name = rs.getString("nombre");
                        float cost = rs.getFloat("precio");
                        String categoriaStr = rs.getString("sector");
                        Categoria categoriaEnum = Categoria.valueOf(categoriaStr.toUpperCase());
                        return new Producto(barcode, name, cost, categoriaEnum);
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            // No se encontró producto
            System.out.println("No se encontró ningún producto con el código " + code);
            return null;
        } catch (Exception e) {
            System.out.println("Error al buscar producto: " + e.getMessage());
            return null;
        }
    }
    public static void registrarVenta(JdbcTemplate jdbcTemplate, Venta venta) {
        Random random = new Random();
        final int numero = random.nextInt(1000); // ID pago
        final int numerox = random.nextInt(1000); // ID venta

        String sqlPago = "INSERT INTO pagos (id_pago, medio_pago, total, fecha) VALUES (?, ?, ?, ?)";
        String sqlVenta = "INSERT INTO ventas (id_venta, fecha, total, id_pago, id_deudor) VALUES (?, ?, ?, ?, ?)";

        try {
            // Insertar en pagos
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlPago, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, numero);
                ps.setString(2, venta.getPago().getMedioPago());
                ps.setFloat(3, venta.getPago().getTotal());
                ps.setDate(4, java.sql.Date.valueOf(venta.getPago().getFecha()));
                return ps;
            });
            System.out.println("✅ Pago registrado correctamente (ID " + numero + ")");
        } catch (Exception e) {
            System.err.println("❌ Error al registrar el pago: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            // Insertar en ventas
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, numerox);
                ps.setDate(2, java.sql.Date.valueOf(venta.getFecha()));
                ps.setFloat(3, venta.getTotal());
                ps.setInt(4, numero); // FK al pago

                if (venta.getDeudor() != null && venta.getDeudor().getDni() != null) {
                    ps.setInt(5, Integer.parseInt(venta.getDeudor().getDni()));
                } else {
                    ps.setNull(5, java.sql.Types.INTEGER);
                }

                return ps;
            });
            System.out.println("✅ Venta registrada correctamente (ID " + numerox + ")");

        } catch (Exception e) {
            System.err.println("❌ Error al registrar la venta: " + e.getMessage());
            e.printStackTrace();
        }
        //Preguntar si luego le aosicio los items a la venta en la base de datos
    }

}