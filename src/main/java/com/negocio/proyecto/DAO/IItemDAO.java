package com.negocio.proyecto.DAO;

import com.negocio.proyecto.Models.Item;

import java.util.List;

public interface IItemDAO {
    void registrarItems(List<Item> items, int id_venta);
}
