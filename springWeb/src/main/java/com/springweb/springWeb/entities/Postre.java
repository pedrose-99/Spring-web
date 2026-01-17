package com.springweb.springWeb.entities;


import jakarta.persistence.*;

@Entity
@Table(name="postres")
public class Postre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="Nombre", nullable = false)
    private String nombre;

    @Column(name="Descripcion")
    private String descripcion;

    @Column(name="Precio", nullable = false)
    private Double precio;

    @Transient
    private Double precioConIVA;

    public Postre() {
    }

    public Postre(String nombre, String descripcion, Double precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getPrecioConIVA() {
        return precio * 1.21;
    }
}
