package com.KaanIsmetOkul.CreditFlux;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CreditFluxApplication {

	public static void main(String[] args) {
//		Dotenv dotenv = Dotenv.load();
//		System.out.println("DB_URL: " + dotenv.get("DB_URL"));
//		System.out.println("DB_USERNAME: " + dotenv.get("DB_USERNAME"));
//		System.out.println("DB_PASSWORD: " + dotenv.get("DB_PASSWORD"));
//		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
		SpringApplication.run(CreditFluxApplication.class, args);
	}

}
