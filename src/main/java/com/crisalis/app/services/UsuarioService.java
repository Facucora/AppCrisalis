package com.crisalis.app.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crisalis.app.dao.PedidoRepository;
import com.crisalis.app.model.Pedido;

@Service
public class UsuarioService {
	
	@Autowired
	private final PedidoRepository pedidoRepository;
	
	public UsuarioService(PedidoRepository pedidoRepository) {
		this.pedidoRepository = pedidoRepository;
	}

	public Pedido savePedido(Pedido pedido) {
			return this.pedidoRepository.save(pedido);
	}
	

	public Iterable<Pedido> getAllPedidos() {
		return this.pedidoRepository.findAll();
	}

	public Optional<Pedido> findPedidoByID(Integer id) {
		return this.pedidoRepository.findById(id);
	}

	public void deletePedido(Integer id) {
		this.pedidoRepository.deleteById(id);
	}

}
