package com.crisalis.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crisalis.app.dao.ClientRepository;
import com.crisalis.app.dao.PedidoRepository;
import com.crisalis.app.model.Cliente;
import com.crisalis.app.model.DetallePedido;
import com.crisalis.app.model.Pedido;
import com.crisalis.app.model.Producto;

@Service
public class ClienteService {
	
	@Autowired
	private final ClientRepository clienteRepo;
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public ClienteService(ClientRepository clienteRepo) {
		this.clienteRepo = clienteRepo;
	}

	public Cliente saveCliente(Cliente cliente) {
			return this.clienteRepo.save(cliente);
	}
	

	public Iterable<Cliente> getAllClientes() {
		return this.clienteRepo.findAll();
	}

	public Optional<Cliente> findClienteByID(Integer id) {
		return this.clienteRepo.findById(id);
	}

	public void deleteCliente(Integer id) {
		this.clienteRepo.deleteById(id);
	}
	
	public List<Producto> getServiciosContratados(Integer clienteId) {
		List<Pedido> pedidos = pedidoRepository.findByClienteId(clienteId);
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
	
	public List<Producto> getProductosContratados(Integer clienteId) {
		List<Pedido> pedidos = pedidoRepository.findByClienteId(clienteId);
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
