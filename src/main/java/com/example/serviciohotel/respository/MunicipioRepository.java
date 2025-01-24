package com.example.serviciohotel.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.serviciohotel.entity.Municipio;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Integer> {
    // JpaRepository ya incluye métodos básicos como findById, findAll, etc.
}