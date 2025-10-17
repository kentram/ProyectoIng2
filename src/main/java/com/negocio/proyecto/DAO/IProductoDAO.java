package com.negocio.proyecto.DAO;

import com.negocio.proyecto.Models.Producto;
//Define qué se puede hacer con productos:
public interface IProductoDAO {
    Producto buscarPorCodigo(String codigo);
}
