package com.crisalis.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crisalis.app.dao.PedidoRepository;
import com.crisalis.app.model.Pedido;

@Service
public class PedidoService {
	
	@Autowired
	private final PedidoRepository pedidoRepository;
	
	public PedidoService(PedidoRepository pedidoRepository) {
		this.pedidoRepository = pedidoRepository;
	}

	public Pedido savePedido(Pedido pedido) {
			return this.pedidoRepository.save(pedido);
	}
	

	public List<Pedido> getAllPedidos() {
		return this.pedidoRepository.findAll();
	}

	public Optional<Pedido> findPedidoByID(Integer id) {
		return this.pedidoRepository.findById(id);
	}

	public void deletePedido(Integer id) {
		this.pedidoRepository.deleteById(id);
	}

	public boolean existsById(Integer id) {
		// TODO Auto-generated method stub
		return this.pedidoRepository.existsById(id);
	}

}
