package com.example.serviciohotel.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.serviciohotel.entity.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    boolean existsByRnt(String rnt);
}
