package aiss.gitminer.model;

import aiss.gitminer.repository.TokenUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private TokenUserRepository tokenUserRepository;

    @Autowired
    public DatabaseLoader(TokenUserRepository tokenUserRepository) {
        this.tokenUserRepository = tokenUserRepository;
    }

    @Override
    public void run(String... strings) throws Exception {

        // Cargar un usuario TokenUser en la base de datos al iniciar la aplicación
        TokenUser user = new TokenUser();
        user.setName("admin");
        user.setEmail("admin@example.com");

        // Codificar la contraseña utilizando BCryptPasswordEncoder
        String password = "admin";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        user.setPassword(hashedPassword);

        tokenUserRepository.save(user);
    }
}


