package com.negocio.proyecto.DAO;
import com.negocio.proyecto.Models.Item;
import com.negocio.proyecto.Models.Venta;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Random;

@Repository
public class VentaDAOImpl implements IVentaDAO {

    private final JdbcTemplate jdbcTemplate;
    private final IPagoDAO pagoDAO;
    private final IItemDAO itemDAO;
    private final IDeudorDAO deudorDAO;
    public VentaDAOImpl(JdbcTemplate jdbcTemplate, IPagoDAO pagoDAO, IItemDAO itemDAO, IDeudorDAO deudorDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.pagoDAO = pagoDAO;
        this.itemDAO = itemDAO;
        this.deudorDAO = deudorDAO;
    }

    @Override
    public void registrarVenta(Venta venta) {
        int id_pago;
        try {
            id_pago = pagoDAO.registrarPago(venta.getPago());
        } catch (Exception e) {
            System.err.println(" Error al registrar el pago antes de la venta: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        String sql = "INSERT INTO ventas (fecha, total, id_pago, id_deudor) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id_venta"});
                ps.setDate(1, java.sql.Date.valueOf(venta.getFecha()));
                ps.setFloat(2, venta.getTotal());
                ps.setInt(3, id_pago);

                if (venta.getDeudor() != null) {
                    ps.setInt(4, venta.getDeudor().getDni());
                    deudorDAO.agregarDeuda(venta.getDeudor(),venta.getTotal());

                } else {
                    ps.setNull(4, java.sql.Types.INTEGER);
                }

                return ps;
            }, keyHolder);
        } catch (Exception e) {
            System.err.println(" Error al registrar la venta: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        int idVenta = ((Number) keyHolder.getKeys().get("id_venta")).intValue();
        System.out.println(" Venta registrada correctamente (ID " + idVenta + ")");

        try {
            itemDAO.registrarItems(venta.getItems(), idVenta);
        } catch (Exception e) {
            System.err.println(" Error al registrar el pago antes de la venta: " + e.getMessage());
            e.printStackTrace();
            return;
        }


    }
}