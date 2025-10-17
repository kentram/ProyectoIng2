package com.negocio.proyecto.DAO;

import com.negocio.proyecto.Models.Categoria;
import com.negocio.proyecto.Models.Deudor;
import com.negocio.proyecto.Models.Producto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class DeudorDAOImpl  implements IDeudorDAO{

    private final JdbcTemplate jdbcTemplate;
    public DeudorDAOImpl(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}


    @Override
    public boolean registrarDeudor(Deudor deudor) {
        String sql = "INSERT INTO deudores (dni, nombre, telefono) VALUES (?, ?, ?)";
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, deudor.getDni());
                ps.setString(2, deudor.getNombre());
                ps.setString(3, deudor.getTelefono());
                return ps;
            });
            System.out.println("Deudor registrado correctamente.");
            return true;
        } catch (Exception e) {
            System.err.println("Error al registrar deudor:");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarDeudor(int dni) {
        String sql = "DELETE FROM deudores WHERE dni = ?";
        try {
            jdbcTemplate.update(sql, dni);
            System.out.println("Deudor Eliminado");
            return true;
        } catch (Exception e) {
            System.err.println("Error al eliminar deudor:");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void agregarDeuda(Deudor deudor, float saldo) {
        String sql = "UPDATE deudores SET saldo_deudor = saldo_deudor + ? WHERE dni = ?";

        try {
            int filas = jdbcTemplate.update(sql, saldo, deudor.getDni());

            if (filas > 0) {
                System.out.println("Saldo del deudor actualizado correctamente (deuda sumada).");
            } else {
                System.out.println("No se encontró ningún deudor con ese DNI.");
            }

        } catch (Exception e) {
            System.err.println("Error al actualizar el saldo del deudor:");
            e.printStackTrace();
        }
    }

    @Override
    public Deudor buscarDeudor(int dni) {
        String sql = "SELECT * FROM deudores WHERE dni = ?";
        try{
            return jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{dni},
                    (rs, rowNum) -> {
                        int doc = rs.getInt("dni");
                        String name = rs.getString("nombre");
                        String tel = rs.getString("telefono");
                        return new Deudor(doc, name, tel);
                    }
            );
        } catch (Exception e){
            System.err.println("Error al buscar deudor:");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Deudor> listarDeudores() {
        String sql = "SELECT * FROM deudores";
        try{
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                Deudor d = new Deudor();
                d.setDni(rs.getInt("dni"));
                d.setNombre(rs.getString("nombre"));
                d.setTelefono(rs.getString("telefono"));
                return d;
            });
        } catch (Exception e) {
            System.err.println("Error al buscar deudor:");
            e.printStackTrace();
            return null;
        }
    }
}
