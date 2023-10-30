package com.impulsione.config;

import com.impulsione.POJO.Cliente;
import com.impulsione.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
public class ClienteDetails implements UserDetails {

    private Cliente cliente;

    @Autowired
    ClienteRepository clienteRepository;

    public ClienteDetails() {
        super();
    }

    public String getRole(){
        return cliente.getRole();
    }

    public Long getId(String username){
        Cliente cliente = clienteRepository.findByUsername(username);
        Long userId = cliente.getId();
        System.out.println("userId: " + userId);
        return userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(cliente.getRole()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return cliente.getPassword();
    }

    @Override
    public String getUsername() {
        return cliente.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
