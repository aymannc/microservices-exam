package com.anc.clientsmanegements;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class ClientsManegementsApplication implements CommandLineRunner {
    final ClientRepository clientRepository;
    final RepositoryRestConfiguration repositoryRestConfiguration;

    public ClientsManegementsApplication(ClientRepository clientRepository,
                                         RepositoryRestConfiguration repositoryRestConfiguration) {
        this.clientRepository = clientRepository;
        this.repositoryRestConfiguration = repositoryRestConfiguration;
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientsManegementsApplication.class, args);
    }

    @Override
    public void run(String... args) {
        repositoryRestConfiguration.exposeIdsFor(Client.class);
        clientRepository.save(new Client(1L, "Ayman", "ayman@nait.com"));
        clientRepository.save(new Client(2L, "Med", "med@med.com"));
        clientRepository.save(new Client(3L, "Saad", "saad@gmail.com"));
    }

}
