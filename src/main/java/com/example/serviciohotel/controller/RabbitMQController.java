package com.example.serviciohotel.controller;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.serviciohotel.entity.Hotel;
import com.example.serviciohotel.service.HotelService;

@RestController
@RequestMapping("/api/v1/rabbitmq")
public class RabbitMQController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private HotelService hotelService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(
        @RequestParam(required = false, defaultValue = "hotelExchange") String exchange, // Valor por defecto
        @RequestParam(required = false, defaultValue = "hotel.key") String routingKey, // Valor por defecto
        @RequestBody String message) {
        
        if (message == null || message.isEmpty()) {
            return ResponseEntity.badRequest().body("El mensaje no puede estar vacío");
        }
        
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        return ResponseEntity.ok("Mensaje enviado exitosamente a RabbitMQ en el exchange: " + exchange + " con routingKey: " + routingKey);
    }

    @GetMapping("/hoteles")
    public ResponseEntity<List<Hotel>> obtenerHoteles() {
        List<Hotel> hoteles = hotelService.obtenerTodosLosHoteles();
        if (hoteles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(hoteles);
    }

    @GetMapping("/mensaje")
    public ResponseEntity<String> recibirMensaje() {
        // Aquí puedes agregar la lógica para recibir un mensaje de RabbitMQ si es necesario
        return ResponseEntity.ok("Este es un nuevo endpoint para recibir mensajes");
    }
}