package com.example.serviciohotel.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.serviciohotel.entity.Hotel;
import com.example.serviciohotel.respository.HotelRepository;

@Service
public class HotelService {

    private static final Logger log = LoggerFactory.getLogger(HotelService.class);

    @Autowired
    private HotelRepository hotelRepository;

    public List<Hotel> obtenerTodosLosHoteles() {
        log.info("Obteniendo todos los hoteles");
        return hotelRepository.findAll();
    }

    public Hotel obtenerHotelPorId(Integer idHotel) {
        log.info("Buscando hotel con id {}", idHotel);
        return hotelRepository.findById(idHotel)
                .orElseThrow(() -> new IllegalArgumentException("Hotel no encontrado con el id: " + idHotel));
    }

    public Hotel insertarHotel(Hotel hotel) {
        if (hotelRepository.existsByRnt(hotel.getRnt())) {
            log.info("El RNT ya está registrado: {}", hotel.getRnt());
            throw new IllegalArgumentException("El RNT ya está registrado.");
        }

        hotel.setEstado(true); // Estado activo
        hotel.setFechaCreacion(LocalDateTime.now());
        hotel.setFechaActualizacion(LocalDateTime.now());
        return hotelRepository.save(hotel);
    }

    public Hotel actualizarHotel(Integer idHotel, Hotel datosActualizados) {
        log.info("Actualizando hotel con id {}", idHotel);
        Hotel hotelExistente = hotelRepository.findById(idHotel)
                .orElseThrow(() -> new IllegalArgumentException("Hotel no encontrado con el id: " + idHotel));

        if (!hotelExistente.getEstado()) {
            if (datosActualizados.getEstado() == null || !datosActualizados.getEstado()) {
                throw new IllegalArgumentException("El hotel está inactivo y solo puede activarse cambiando el estado a 1.");
            }
            hotelExistente.setEstado(true);
        }

        if (datosActualizados.getNombre() != null) {
            hotelExistente.setNombre(datosActualizados.getNombre());
        }
        if (datosActualizados.getNit() != null) {
            hotelExistente.setNit(datosActualizados.getNit());
        }
        if (datosActualizados.getRnt() != null) {
            if (!datosActualizados.getRnt().equals(hotelExistente.getRnt())
                    && hotelRepository.existsByRnt(datosActualizados.getRnt())) {
                throw new IllegalArgumentException("El RNT ya está registrado.");
            }
            hotelExistente.setRnt(datosActualizados.getRnt());
        }
        if (datosActualizados.getMatriculaMercantil() != null) {
            hotelExistente.setMatriculaMercantil(datosActualizados.getMatriculaMercantil());
        }
        if (datosActualizados.getDireccion() != null) {
            hotelExistente.setDireccion(datosActualizados.getDireccion());
        }
        if (datosActualizados.getEmail() != null) {
            hotelExistente.setEmail(datosActualizados.getEmail());
        }
        if (datosActualizados.getTelefono() != null) {
            hotelExistente.setTelefono(datosActualizados.getTelefono());
        }
        if (datosActualizados.getNombreRepLegal() != null) {
            hotelExistente.setNombreRepLegal(datosActualizados.getNombreRepLegal());
        }
        if (datosActualizados.getTelefonoRepLegal() != null) {
            hotelExistente.setTelefonoRepLegal(datosActualizados.getTelefonoRepLegal());
        }
        if (datosActualizados.getEmailRepLegal() != null) {
            hotelExistente.setEmailRepLegal(datosActualizados.getEmailRepLegal());
        }
        if (datosActualizados.getNombreContacto() != null) {
            hotelExistente.setNombreContacto(datosActualizados.getNombreContacto());
        }
        if (datosActualizados.getTelefonoContacto() != null) {
            hotelExistente.setTelefonoContacto(datosActualizados.getTelefonoContacto());
        }

        hotelExistente.setFechaActualizacion(LocalDateTime.now());
        return hotelRepository.save(hotelExistente);
    }

    public void eliminarHotel(Integer idHotel) {
        log.info("Eliminando (borrado lógico) hotel con id {}", idHotel);
        Hotel hotel = hotelRepository.findById(idHotel)
                .orElseThrow(() -> new IllegalArgumentException("Hotel no encontrado con el id: " + idHotel));
        hotel.setEstado(false);
        hotel.setFechaActualizacion(LocalDateTime.now());
        hotelRepository.save(hotel);
    }
}
