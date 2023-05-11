package aiss.gitminer.repository;

import aiss.gitminer.model.TokenUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenUserRepository extends JpaRepository<TokenUser,Integer> {
    Optional<TokenUser> findOneByEmail(String email);

}
