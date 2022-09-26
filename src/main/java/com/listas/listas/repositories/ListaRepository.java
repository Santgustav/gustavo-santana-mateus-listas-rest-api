package com.listas.listas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.listas.listas.entities.ListaEntity;

@Repository
public interface ListaRepository extends JpaRepository<ListaEntity, Long> {

}
