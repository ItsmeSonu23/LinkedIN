package com.clickedin.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.clickedin.features.authentication.repositories.AuthenticationUserRepository;
import com.clickedin.features.authentication.utils.Encoder;
import com.clickedin.features.authentication.model.AuthenticationUser;

@Configuration
public class LoadDatabaseConfiguration {

    private final Encoder encoder;
    
    public LoadDatabaseConfiguration(Encoder encoder) {
        this.encoder = encoder;
    }

    @Bean
    public CommandLineRunner initDatabase(AuthenticationUserRepository authenticationUserRepository){
        return args-> {
            AuthenticationUser authenticationUser  = new AuthenticationUser("sonumish3180@gmail.com",encoder.encode("admin@123"));
            authenticationUserRepository.save(authenticationUser);
        };
    }

}
