package com.example.serviciohotel.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rabbitmq")
public class RabbitMQController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

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
}
