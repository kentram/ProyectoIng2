package com.negocio.proyecto;
import ConnectionsDataBase.ConnectionSingleton;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.sql.Connection;

@SpringBootApplication
@ComponentScan(basePackages = {"com.negocio.proyecto", "Models"})
public class ProyectoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoApplication.class, args);
	}

}
