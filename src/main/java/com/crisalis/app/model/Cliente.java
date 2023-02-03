package com.crisalis.app.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(schema = "dbo", name = "cliente")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;
	@Column(name = "nombre")
    private String nombre;
	@Column(name = "apellido")
    private String apellido;
	@Column(name = "dni")
    private String dni;
	@JsonIgnore 

	 @ManyToMany(fetch = FetchType.LAZY)
	    @JoinTable(
	        name = "cliente_producto", 
	        joinColumns = @JoinColumn(name = "cliente_id"), 
	        inverseJoinColumns = @JoinColumn(name = "producto_id")
	    )
	    private List<Producto> productosContratados = new ArrayList<>();

	
	public Cliente(Integer id, String nombre, String apellido, String dni) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
	}
	public Cliente(){
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


	public String getApellido() {
		return apellido;
	}


	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	public String getDni() {
		return dni;
	}


	public void setDni(String dni) {
		this.dni = dni;
	}
    
    
}
