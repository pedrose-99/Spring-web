package com.springweb.springWeb.entities;


import jakarta.persistence.*;
import com.springweb.springWeb.entities.Postre;

@Entity
@Table(name="menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="Nombre")
    private String nombre;

    @Column(name="Descripcion")
    private String descripcion;

    @Column(name="Precio_Menu")
    private Double precio;

    @ManyToOne //Muchos a uno
    @JoinColumn(name="Entrante_id") //Clave foranea
    private Entrante entrante;

    @ManyToOne
    @JoinColumn(name="Principal_id")
    private Principal principal;

    @ManyToOne
    @JoinColumn(name="Postre_id")
    private Postre postre;


    public Menu() {
    }

    public Menu(String nombre, String descripcion, Double precio, Entrante entrante, Principal principal, Postre postre) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.entrante = entrante;
        this.principal = principal;
        this.postre = postre;
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

    public Entrante getEntrante() {
        return entrante;
    }

    public void setEntrante(Entrante entrante) {
        this.entrante = entrante;
    }

    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    public Postre getPostre() {
        return postre;
    }

    public void setPostre(Postre postre) {
        this.postre = postre;
    }
}
