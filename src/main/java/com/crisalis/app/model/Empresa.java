package com.crisalis.app.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(schema = "dbo", name = "empresa")
public class Empresa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;
	@Column(name = "nombre")
    private String nombre;
	@Column(name = "cuit")
    private String cuit;
	@Column(name = "razonsocial")
    private String razonsocial;
	@Column(name = "fechainicio")
    private String fechainicio;

	@OneToOne(fetch = FetchType.EAGER,optional = false)
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	@JsonIgnore 

	 @ManyToMany(fetch = FetchType.LAZY)
	    @JoinTable(
	        name = "empresa_producto", 
	        joinColumns = @JoinColumn(name = "empresa_id"), 
	        inverseJoinColumns = @JoinColumn(name = "producto_id")
	    )
	    private List<Producto> productosContratados = new ArrayList<>();
	
	public Empresa(Integer id, String nombre, String cuit, String razonsocial, String fechainicio, Cliente cliente) {
		this.id = id;
		this.nombre = nombre;
		this.cuit = cuit;
		this.razonsocial = razonsocial;
		this.fechainicio = fechainicio;
		this.cliente = cliente;
	}
	
	public Empresa(){
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


	public String getCuit() {
		return cuit;
	}


	public void setCuit(String cuit) {
		this.cuit = cuit;
	}


	public String getRazonsocial() {
		return razonsocial;
	}


	public void setRazonsocial(String razonsocial) {
		this.razonsocial = razonsocial;
	}


	public String getFechainicio() {
		return fechainicio;
	}


	public void setFechainicio(String fechainicio) {
		this.fechainicio = fechainicio;
	}


	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


   
}
