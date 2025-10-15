package Models;
import java.util.ArrayList;

public class Deudor {
    private String dni;
    private String nombre;
    private String telefono;
    private double saldoDeudor;
    private boolean activo;

    public Deudor() {}

    public Deudor(String dni, String nombre, String telefono,double saldoDeudor, boolean activo) {
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
        this.saldoDeudor = 0.0; // Regla de negocio: siempre inicia en 0
        this.activo = true;     // Para eliminación lógica
    }

    // --- Getters y Setters ---
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public double getSaldoDeudor() { return saldoDeudor; }
    public void setSaldoDeudor(double saldoDeudor) { this.saldoDeudor = saldoDeudor; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    //  met de la clase
    public void actualizarSaldo(double monto) {
        this.saldoDeudor += monto;
    }

    @Override
    public String toString() {
        String estado = this.activo ? "Activo" : "Inactivo";
        return String.format("Deudor: %s (DNI: %s) | Tel: %s | Saldo: $%.2f | Estado: %s",
                this.nombre, this.dni, this.telefono, this.saldoDeudor, estado);
    }



}
