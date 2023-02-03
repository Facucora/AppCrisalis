package com.crisalis.app.model;

import javax.persistence.*;




@Entity
@Table(schema = "dbo", name = "impuesto")
public class Impuestos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;
	@Column(name = "nombre")
    private String nombre;
	@Column(name = "porcentaje")
    private float porcentaje;
	 
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
	public float getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(float porcentaje) {
		this.porcentaje = porcentaje;
	}
	
	
	
	public Impuestos(Integer id, String nombre, float porcentaje) {
		this.id = id;
		this.nombre = nombre;
		this.porcentaje = porcentaje;
	}
	public Impuestos() {
		
	}
}
