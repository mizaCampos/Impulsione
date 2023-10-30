package com.impulsione.repositories;

import com.impulsione.POJO.Cliente;
import com.impulsione.POJO.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByUsername(String username);
    Cliente findByVerificationCode(String code);


}
