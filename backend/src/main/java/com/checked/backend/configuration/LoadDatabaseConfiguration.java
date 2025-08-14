package com.checked.backend.configuration;

import com.checked.backend.features.authentication.model.AuthenticationUser;
import com.checked.backend.features.authentication.repository.AuthenticationUserRepo;
import com.checked.backend.features.authentication.utils.Encoder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabaseConfiguration {

    private final Encoder encoder;

    public LoadDatabaseConfiguration(Encoder encoder) {
        this.encoder = encoder;
    }

    @Bean
    public CommandLineRunner initDatabase(AuthenticationUserRepo authenticationUserRepo){
        return args->{
            AuthenticationUser authenticationUser = new AuthenticationUser("sonu@example.com",encoder.encode("Gurukripa"));
            authenticationUserRepo.save(authenticationUser);
        };
    }
}
