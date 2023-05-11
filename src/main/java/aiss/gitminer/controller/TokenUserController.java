package aiss.gitminer.controller;

import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Project;
import aiss.gitminer.model.TokenUser;
import aiss.gitminer.repository.TokenUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("gitminer/tokenUser")
public class TokenUserController {

    @Autowired
    TokenUserRepository tokenUserRepository;

    @GetMapping
    public List<TokenUser> findAll(){
        return tokenUserRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @PermitAll
    public TokenUser create(@Valid @RequestBody TokenUser tokenUser ){

        String pass = tokenUser.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(pass);
        TokenUser createdUser = tokenUserRepository.save(new TokenUser(tokenUser.getName(),
                tokenUser.getEmail(),
                hashedPassword));

        return createdUser;

    }

}
