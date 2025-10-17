package com.negocio.proyecto.Models;
import java.util.ArrayList;
public class Deudor {
    private int dni;
    private String nombre;
    private String telefono;


    public Deudor() {}

    // --- Constructor con parámetros ---
    public Deudor(int dni, String nombre, String telefono) {
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
    }


    public int getDni() {
        return dni;
    }
    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    @Override
    public String toString() {
        return String.format("Deudor: %s (DNI: %d) | Tel: %s",
                this.nombre, this.dni, this.telefono);
    }
}
