package com.springweb.springWeb.entities;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(name="Nombre", nullable = false, unique = true)
    private String nombre;

    @Size(max = 255, message = "La descripcion no puede superar los 255 caracteres")
    @Column(name="Descripcion")
    private String descripcion;

    @Min(value = 0, message = "El precio no puede ser negativo")
    @Column(name="Precio_Menu")
    private Double precio;

    @NotNull(message = "El entrante es obligatorio")
    @ManyToOne //Muchos a uno
    @JoinColumn(name="Entrante_id", nullable = false) //Clave foranea
    private Entrante entrante;

    @NotNull(message = "El principal es obligatorio")
    @ManyToOne
    @JoinColumn(name="Principal_id", nullable = false)
    private Principal principal;

    @NotNull(message = "El postre es obligatorio")
    @ManyToOne
    @JoinColumn(name="Postre_id", nullable = false)
    private Postre postre;

    @Transient
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Double precioConIVA;

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
        double total = 0.0;
        if (entrante != null && entrante.getPrecio() != null) total += entrante.getPrecio();
        if (principal != null && principal.getPrecio() != null) total += principal.getPrecio();
        if (postre != null && postre.getPrecio() != null) total += postre.getPrecio();
        return total;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getPrecioConIVA() {
        return getPrecio() * 1.21;
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
