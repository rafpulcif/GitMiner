package aiss.gitminer.security;

import aiss.gitminer.model.TokenUser;
import aiss.gitminer.repository.TokenUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private TokenUserRepository tokenUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        TokenUser tokenUser =tokenUserRepository
                .findOneByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("El usuario con email "+email+" no existe"));

        return new UserDetailsImpl(tokenUser);

    }
}
