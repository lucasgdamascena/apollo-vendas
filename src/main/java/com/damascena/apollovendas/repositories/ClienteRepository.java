package com.damascena.apollovendas.repositories;

import com.damascena.apollovendas.domains.Cliente;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByDocumento(String Documento);

    @ReadOnlyProperty
    Cliente findByEmail(String Email);
}