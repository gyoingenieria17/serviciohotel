package com.example.serviciohotel.controller;

import com.example.serviciohotel.entity.Hotel;
import com.example.serviciohotel.service.HotelService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotel")
public class HotelController {

    private static final Logger log = LoggerFactory.getLogger(HotelController.class);

    @Autowired
    private HotelService hotelService;

    /**
     * Obtener todos los hoteles.
     */
    @GetMapping
    public ResponseEntity<List<Hotel>> obtenerHoteles() {
        List<Hotel> hoteles = hotelService.obtenerTodosLosHoteles();
        if (hoteles.isEmpty()) {
            log.info("No se encontraron hoteles.");
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        log.info("Se encontraron {} hoteles.", hoteles.size());
        return ResponseEntity.ok(hoteles); // 200 OK
    }

    /**
     * Obtener un hotel por ID.
     */
    @GetMapping("/{idHotel}")
    public ResponseEntity<Hotel> obtenerHotelPorId(@PathVariable Integer idHotel) {
        try {
            Hotel hotel = hotelService.obtenerHotelPorId(idHotel);
            return ResponseEntity.ok(hotel); // 200 OK
        } catch (IllegalArgumentException e) {
            log.error("Error al buscar el hotel con id {}: {}", idHotel, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 Not Found
        }
    }

    /**
     * Insertar un nuevo hotel.
     */
    @PostMapping
    public ResponseEntity<Hotel> insertarHotel(@RequestBody Hotel hotel) {
        try {
            Hotel hotelInsertado = hotelService.insertarHotel(hotel);
            log.info("Hotel insertado exitosamente: {}", hotelInsertado);
            return ResponseEntity.status(HttpStatus.CREATED).body(hotelInsertado); // 201 Created
        } catch (IllegalArgumentException e) {
            log.error("Error al insertar el hotel: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
    }

    /**
     * Actualizar un hotel por ID.
     */
    @PutMapping("/{idHotel}")
    public ResponseEntity<Hotel> actualizarHotel(
            @PathVariable Integer idHotel,
            @RequestBody Hotel datosActualizados) {
        try {
            Hotel hotelActualizado = hotelService.actualizarHotel(idHotel, datosActualizados);
            log.info("Hotel actualizado exitosamente: {}", hotelActualizado);
            return ResponseEntity.ok(hotelActualizado); // 200 OK
        } catch (IllegalArgumentException e) {
            log.error("Error al actualizar el hotel con id {}: {}", idHotel, e.getMessage());
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
    }

    /**
     * Eliminar (borrado lógico) un hotel.
     */
    @DeleteMapping("/{idHotel}")
    public ResponseEntity<String> eliminarHotel(@PathVariable Integer idHotel) {
        try {
            hotelService.eliminarHotel(idHotel);
            log.info("Hotel con id {} eliminado (borrado lógico).", idHotel);
            return ResponseEntity.ok("Hotel eliminado correctamente."); // 200 OK
        } catch (IllegalArgumentException e) {
            log.error("Error al eliminar el hotel con id {}: {}", idHotel, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // 404 Not Found
        }
    }
}
