package Models;

import java.time.LocalDate;

public class Pago {
    private float total;
    private MedioPago medioPago;
    private LocalDate fecha;

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
    public String getMedioPago() {
        return medioPago.name();
    }


    public void setMedioPago(MedioPago medioPago) {
        this.medioPago = medioPago;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
