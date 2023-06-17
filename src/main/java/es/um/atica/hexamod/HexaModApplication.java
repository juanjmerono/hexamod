package es.um.atica.hexamod;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ComponentScan(basePackages = {"es.um.atica.hexamod"})
public class HexaModApplication {
    
    public static void main(String[] args) {
		SpringApplication.run(HexaModApplication.class, args);
	}

}
