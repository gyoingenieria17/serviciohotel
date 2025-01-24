package com.example.serviciohotel.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idhotel")
    private Integer idHotel;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "nit", nullable = false, unique = true, length = 20)
    private String nit;

    @Column(name = "rnt", nullable = false, unique = true, length = 20)
    private String rnt;

    @Column(name = "matricula_mercantil", nullable = false, length = 20)
    private String matriculaMercantil;

    @Column(name = "direccion", length = 255)
    private String direccion;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "nombre_rep_legal", length = 100)
    private String nombreRepLegal;

    @Column(name = "telefono_rep_legal", length = 20)
    private String telefonoRepLegal;

    @Column(name = "email_rep_legal", length = 100)
    private String emailRepLegal;

    @Column(name = "nombre_contacto", length = 100)
    private String nombreContacto;

    @Column(name = "telefono_contacto", length = 20)
    private String telefonoContacto;

    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @ManyToOne
    @JoinColumn(name = "idmunicipio", nullable = false)
    private Municipio municipio;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }
}
