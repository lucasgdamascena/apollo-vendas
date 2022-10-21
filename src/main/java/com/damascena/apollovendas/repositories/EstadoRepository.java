package com.damascena.apollovendas.repositories;

import com.damascena.apollovendas.domains.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

    public List<Estado> findAllByOrderByNome();
}