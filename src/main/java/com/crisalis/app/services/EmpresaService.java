package com.crisalis.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crisalis.app.dao.EmpresaRepository;
import com.crisalis.app.dao.PedidoRepository;
import com.crisalis.app.model.DetallePedido;
import com.crisalis.app.model.Empresa;
import com.crisalis.app.model.Pedido;
import com.crisalis.app.model.Producto;

@Service
public class EmpresaService {
	
	@Autowired
	private final EmpresaRepository empresaRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private ClienteService clienteService;
	
	public EmpresaService(EmpresaRepository empresaRepository) {
		this.empresaRepository = empresaRepository;
	}

	public Empresa saveEmpresa(Empresa empresa) {
			return this.empresaRepository.save(empresa);
	}
	

	public Iterable<Empresa> getAllEmpresa() {
		return this.empresaRepository.findAll();
	}

	public Optional<Empresa> findEmpresaByID(Integer id) {
		return this.empresaRepository.findById(id);
	}

	public void deleteEmpresa(Integer id) {
		this.empresaRepository.deleteById(id);
	}
	
	public List<Producto> getServiciosContratados(Integer empresaId) {
		List<Pedido> pedidos = pedidoRepository.findByClienteId(empresaId);
		List<Producto> serviciosContratados = new ArrayList<>();
		for (Pedido pedido : pedidos) {
			for (DetallePedido detalle : pedido.getDetalles()) {
				Producto producto = detalle.getProducto();
				if (producto.getTipo().equals("Servicio")) {
					serviciosContratados.add(producto);
				}
			}
		}
		System.out.println(serviciosContratados);
		return serviciosContratados;
		
	}
	
	public List<Producto> getProductosContratados(Integer empresaId) {
		List<Pedido> pedidos = pedidoRepository.findByClienteId(empresaId);
		List<Producto> serviciosContratados = new ArrayList<>();
		for (Pedido pedido : pedidos) {
			for (DetallePedido detalle : pedido.getDetalles()) {
				Producto producto = detalle.getProducto();
					serviciosContratados.add(producto);
			}
		}
		System.out.println(serviciosContratados);
		return serviciosContratados;
		
	}

}
