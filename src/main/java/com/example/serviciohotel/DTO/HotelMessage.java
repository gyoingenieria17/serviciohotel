package com.example.serviciohotel.DTO;

import com.example.serviciohotel.entity.Hotel;
import com.example.serviciohotel.entity.Municipio;
import lombok.Data;

@Data
public class HotelMessage {
    private String operation; // "INSERT" o "UPDATE"
    private Integer idHotel; // Solo para actualizaciones
    private String nombre;
    private String nit;
    private String rnt;
    private String matriculaMercantil;
    private String direccion;
    private String email;
    private String telefono;
    private String nombreRepLegal;
    private String telefonoRepLegal;
    private String emailRepLegal;
    private String nombreContacto;
    private String telefonoContacto;
    private Integer idMunicipio;
    private Boolean estado;

    public Hotel toHotel() {
        Hotel hotel = new Hotel();
        hotel.setNombre(nombre);
        hotel.setNit(nit);
        hotel.setRnt(rnt);
        hotel.setMatriculaMercantil(matriculaMercantil);
        hotel.setDireccion(direccion);
        hotel.setEmail(email);
        hotel.setTelefono(telefono);
        hotel.setNombreRepLegal(nombreRepLegal);
        hotel.setTelefonoRepLegal(telefonoRepLegal);
        hotel.setEmailRepLegal(emailRepLegal);
        hotel.setNombreContacto(nombreContacto);
        hotel.setTelefonoContacto(telefonoContacto);
        hotel.setEstado(estado);

        if (idMunicipio != null) {
            Municipio municipio = new Municipio();
            municipio.setIdMunicipio(idMunicipio);
            hotel.setMunicipio(municipio);
        }

        return hotel;
    }
}
