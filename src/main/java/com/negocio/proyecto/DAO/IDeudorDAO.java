package com.negocio.proyecto.DAO;

import com.negocio.proyecto.Models.Deudor;
import java.util.List;

public interface IDeudorDAO {
    boolean registrarDeudor(Deudor deudor);
    boolean eliminarDeudor(int dni);
    void agregarDeuda(Deudor deudor,float valor);
    Deudor buscarDeudor(int dni);
    List<Deudor> listarDeudores();
}
