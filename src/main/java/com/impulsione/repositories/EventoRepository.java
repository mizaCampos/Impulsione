package com.impulsione.repositories;

import com.impulsione.POJO.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    @Query("select c from Evento as c where c.eventName like %?1%")
    List<Evento> findEventoByName(String name);

    List<Evento> findByCategory(String name);

    List<Evento> findByClienteId(Long clienteId);


}
