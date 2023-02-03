package com.crisalis.app.model;

import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(schema = "dbo", name = "producto")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Producto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;
	@Column(name = "nombre")
    private String nombre;
	@Column(name = "tipo")
    private String tipo;
	@Column(name = "precio")
    private Double precio;
	@Column(name = "soporte")
    private Boolean soporte = false;
	
	
	public Producto(Integer id, String nombre, String tipo, Double precio) {
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.precio = precio;
	}
	public Producto(){
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
	public Boolean getSoporte() {
		return soporte;
	}
	public void setSoporte(Boolean soporte) {
		this.soporte = soporte;
	}
}
