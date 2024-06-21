package org.projekt1.server.service;

import org.projekt1.server.model.Mitarbeiter;
import org.projekt1.server.repository.MitarbeiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Mitarbeiter mitarbeiter = mitarbeiterRepository.findByUsername(username);
        if (mitarbeiter == null) {
            throw new UsernameNotFoundException("Benutzer nicht gefunden");
        }
        return User.withUsername(mitarbeiter.getUsername())
                .password(mitarbeiter.getPassword())
                .roles(mitarbeiter.getRolle())
                .build();
    }
}
