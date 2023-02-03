package com.crisalis.app.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(schema = "dbo", name = "pedido")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;
	@Column(name = "precioTotal")
    private Double precioTotal;
	@Column(name = "fechaCreacion")
	private LocalDate fechaCreacion = LocalDate.now(); 
	@JoinColumn(name="cliente_id")
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	private Cliente cliente;
	@JoinColumn(name="empresa_id")
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	private Empresa empresa;
	@JsonIgnore 
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
	private List<DetallePedido> detalles = new ArrayList<>();
	

	public Integer getId() {
		return id;
	}


	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public List<DetallePedido> getDetalles() {
		return detalles;
	}


	public void setDetalles(List<DetallePedido> detalles) {
		this.detalles = detalles;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Double getPrecio() {
		return precioTotal;
	}


	public void setPrecio(Double precioTotal) {
		this.precioTotal = precioTotal;
	}
	public Empresa getEmpresa() {
		return empresa;
	}


	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}


	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
    
}
