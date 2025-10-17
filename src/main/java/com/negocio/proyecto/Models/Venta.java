package com.negocio.proyecto.Models;

import java.time.LocalDate;
import java.util.List;

public class Venta {
    private LocalDate fecha;
    private Pago pago;
    private Deudor deudor;
    private float total;
    private List<Item> items;

    // Getter y Setter para fecha
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    // Getter y Setter para pago
    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    // Getter y Setter para deudor
    public Deudor getDeudor() {
        return deudor;
    }

    public void setDeudor(Deudor deudor) {
        this.deudor = deudor;
    }

    // Getter y Setter para total
    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    // Getter y Setter para items
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
