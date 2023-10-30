package com.impulsione.config;

import com.impulsione.POJO.Cliente;
import com.impulsione.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

@Configuration
public class ClienteServiceConfig implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByUsername(username);

        if (cliente == null) {
            throw new UsernameNotFoundException("Could not find username");
        }

        // Supondo que o papel do administrador seja representado como uma String 'roleName' em Admin
        String clienteRole = cliente.getRole();

        // única autoridade com base na String do papel do administrador
        GrantedAuthority authority = new SimpleGrantedAuthority(clienteRole);

        // Crie um UserDetails com a autoridade
        UserDetails userDetails = new User(
                cliente.getUsername(),
                cliente.getPassword(),
                Collections.singletonList(authority) // Use Collections.singletonList para criar uma lista com uma única autoridade
        );

        return userDetails;
    }
}
