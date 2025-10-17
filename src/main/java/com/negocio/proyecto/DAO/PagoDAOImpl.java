package com.negocio.proyecto.DAO;

import com.negocio.proyecto.Models.Pago;
import com.negocio.proyecto.Models.Venta;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Random;

@Repository
public class PagoDAOImpl implements IPagoDAO {
    private final JdbcTemplate jdbcTemplate;

    public PagoDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public int registrarPago(Pago pago) {
        Random random = new Random();
        final int id_pago = random.nextInt(1000); // ID pago de momento hasta que la funcion del franco me el numero de operacion
        String sql = "INSERT INTO pagos (id_pago, medio_pago, total, fecha) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, id_pago);
                ps.setString(2, pago.getMedioPago());
                ps.setFloat(3, pago.getTotal());
                ps.setDate(4, java.sql.Date.valueOf(pago.getFecha()));
                return ps;
            });
            System.out.println("Pago registrado correctamente (ID " + id_pago + ")");
            return id_pago;
        } catch (Exception e) {
            System.err.println("Error al registrar el pago: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }
}
