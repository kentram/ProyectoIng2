package com.negocio.proyecto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class ProyectoApplication {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProyectoApplication(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProyectoApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {

            String[] createTableStatements = {
                    "CREATE TABLE IF NOT EXISTS public.producto (" +
                            "id_producto serial PRIMARY KEY, " +
                            "nombre character varying(100) NOT NULL, " +
                            "precio numeric(10,2) NOT NULL, " +
                            "sector character varying(100) NOT NULL" +
                            ");",

                    "CREATE TABLE IF NOT EXISTS public.pagos (" +
                            "id_pago serial PRIMARY KEY, " +
                            "medio_pago character varying(20) NOT NULL, " +
                            "total numeric(10,2) NOT NULL, " +
                            "fecha date NOT NULL" +
                            ");",

                    "CREATE TABLE IF NOT EXISTS public.deudores (" +
                            "dni INTEGER PRIMARY KEY, " +
                            "nombre VARCHAR(100) NOT NULL, " +
                            "telefono VARCHAR(20), " +
                            "saldo_deudor NUMERIC(10,2) DEFAULT 0" +
                            ");",

                    "CREATE TABLE IF NOT EXISTS public.codigo_barra (" +
                            "id serial PRIMARY KEY, " +
                            "id_producto integer REFERENCES public.producto(id_producto), " +
                            "codigo character varying(100) UNIQUE NOT NULL" +
                            ");",

                    "CREATE TABLE IF NOT EXISTS public.ventas (" +
                            "id_venta serial PRIMARY KEY, " +
                            "fecha date NOT NULL, " +
                            "total numeric(10,2) NOT NULL, " +
                            "id_pago integer NOT NULL REFERENCES public.pagos(id_pago), " +
                            "id_deudor integer REFERENCES public.deudores(dni)" +
                            ");",

                    "CREATE TABLE IF NOT EXISTS public.items_vendidos (" +
                            "id_item serial PRIMARY KEY, " +
                            "id_venta integer REFERENCES public.ventas(id_venta) ON DELETE CASCADE, " +
                            "nombre character varying(100), " +
                            "cantidad integer NOT NULL, " +
                            "precio_unitario numeric(10,2) NOT NULL, " +
                            "total numeric(10,2) NOT NULL" +
                            ");",

                    "CREATE TABLE IF NOT EXISTS public.listado (" +
                            "id serial PRIMARY KEY, " +
                            "id_codigo integer NOT NULL REFERENCES public.codigo_barra(id), " +
                            "cantidad integer DEFAULT 0" +
                            ");" /*,
                    "INSERT INTO producto (nombre, precio, sector) VALUES ('Salsa pizza', 950.00, 'CERAMICOS');",
                    "INSERT INTO producto (nombre, precio, sector) VALUES ('Puré de tomate Marolio', 820.00, 'SAUMERIOS');",
                    "INSERT INTO producto (nombre, precio, sector) VALUES ('Picadillo de carne Swift', 1050.00, 'CERAMICOS');",
                    "INSERT INTO codigo_barra (id_producto, codigo) VALUES (1, '7798138553019');",
                    "INSERT INTO codigo_barra (id_producto, codigo) VALUES (2, '7797470199121');",
                    "INSERT INTO codigo_barra (id_producto, codigo) VALUES (3, '7790360720115');",
                    "INSERT INTO listado (id_codigo, cantidad) VALUES (1, 20);",
                    "INSERT INTO listado (id_codigo, cantidad) VALUES (2, 35);",
                    "INSERT INTO listado (id_codigo, cantidad) VALUES (3, 15);"
                    */


            };

            try {
                for (String sql : createTableStatements) {
                    jdbcTemplate.execute(sql);
                }
                System.out.println("Tablas creadas o ya existentes.");
            } catch (Exception e) {
                System.err.println("Error al inicializar la base de datos: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}
