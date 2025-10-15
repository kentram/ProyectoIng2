package Models;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service; // Importar esto

// ANOTACIÓN CLAVE
@Service
public class DeudorService {
    private List<Deudor> listaDeudores;

    public DeudorService() {
        listaDeudores = new ArrayList<>();

        // Datos de prueba — AHORA ESTOS DATOS SE CARGARÁN UNA SOLA VEZ
        listaDeudores.add(new Deudor("12345678", "Juan Pérez", "1155550000", 1500.0, true));
        listaDeudores.add(new Deudor("87654321", "María Gómez", "1166661111", 250.75, true));
    }


    // alta
    public boolean altaDeudor(Deudor deudor) {
        // Verificar si ya existe un deudor con el mismo DNI
        for (Deudor d : listaDeudores) {
            if (d.getDni().equals(deudor.getDni())) {
                return false; // Ya existe
            }
        }

        listaDeudores.add(deudor);
        return true; // Alta exitosa
    }

    // baja

    public boolean bajaDeudor(String dni) {
        for (Deudor d : listaDeudores) {
            if (d.getDni().equals(dni) && d.isActivo()) {
                d.setActivo(false);
                System.out.println("Deudor " + d.getNombre() + " dado de baja (lógica)");
                return true;
            }
        }
        return false;
    }




    // busqueda
    public Deudor buscarDeudor(String dni) {
        for (Deudor d : listaDeudores) {
            if (d.getDni().equals(dni)) {
                return d;
            }
        }
        return null;
    }
    public List<Deudor> getListaDeudores() {
        return listaDeudores;
    }


    // unu lista
    public void listarDeudores() {
        for (Deudor d : listaDeudores) {
            System.out.println(d);
        }
    }
    public List<Deudor> listarActivos() {
        List<Deudor> activos = new ArrayList<>();
        for (Deudor d : listaDeudores) {
            if (d.isActivo()) activos.add(d);
        }
        return activos;
    }


}