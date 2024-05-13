package br.com.desafio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableHystrix
public class TransferenciaApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransferenciaApplication.class, args);
    }
}
