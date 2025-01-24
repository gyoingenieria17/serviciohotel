package com.example.serviciohotel.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.example.serviciohotel.DTO.HotelMessage;
import com.example.serviciohotel.config.RabbitMQConfig;
import com.example.serviciohotel.service.HotelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.springframework.messaging.handler.annotation.Header;


@Component
public class RabbitMQListener {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQListener.class);

    private final HotelService hotelService;
    private final ObjectMapper objectMapper; // Para convertir el mensaje JSON en un objeto

    public RabbitMQListener(HotelService hotelService) {
        this.hotelService = hotelService;
        this.objectMapper = new ObjectMapper();
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME, ackMode = "MANUAL")
    public void handleMessage(String messageBody, 
                              Channel channel, 
                              @Header("amqp_deliveryTag") long deliveryTag) {
        log.info("Mensaje recibido desde RabbitMQ: {}", messageBody);

        try {
            // Convierte el mensaje JSON en un objeto HotelMessage
            HotelMessage hotelMessage = objectMapper.readValue(messageBody, HotelMessage.class);

            // Procesa la operación
            switch (hotelMessage.getOperation().toUpperCase()) {
                case "INSERT":
                    hotelService.insertarHotel(hotelMessage.toHotel());
                    log.info("Hotel insertado exitosamente: {}", hotelMessage.getNombre());
                    break;

                case "UPDATE":
                    if (hotelMessage.getIdHotel() == null) {
                        throw new IllegalArgumentException("El ID del hotel es necesario para actualizar.");
                    }
                    hotelService.actualizarHotel(hotelMessage.getIdHotel(), hotelMessage.toHotel());
                    log.info("Hotel actualizado exitosamente: {}", hotelMessage.getIdHotel());
                    break;

                default:
                    throw new IllegalArgumentException("Operación no soportada: " + hotelMessage.getOperation());
            }

            // Confirma que el mensaje fue procesado exitosamente
            channel.basicAck(deliveryTag, false);
            log.info("Mensaje confirmado: {}", messageBody);

        } catch (IllegalArgumentException e) {
            log.error("Error de validación en el mensaje: {}", messageBody, e);
            try {
                // Rechaza el mensaje sin reencolarlo
                channel.basicReject(deliveryTag, false);
            } catch (Exception rejectEx) {
                log.error("Error al rechazar el mensaje: {}", messageBody, rejectEx);
            }
        } catch (Exception e) {
            log.error("Error procesando el mensaje: {}", messageBody, e);
            try {
                // Rechaza el mensaje y lo reencola para intentarlo nuevamente
                channel.basicNack(deliveryTag, false, true);
            } catch (Exception nackEx) {
                log.error("Error al reenviar el mensaje a la cola: {}", messageBody, nackEx);
            }
        }
    }
}
