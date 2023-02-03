package com.crisalis.app.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crisalis.app.dao.DetallePedidoRepository;
import com.crisalis.app.model.DetallePedido;

@Service
public class DetallePedidoService {
	
	@Autowired
	private final DetallePedidoRepository detallePedidoRepository;
	
	public DetallePedidoService(DetallePedidoRepository detallePedidoRepository) {
		this.detallePedidoRepository = detallePedidoRepository;
	}

	public DetallePedido saveDetallePedido(DetallePedido detallePedido) {
			return this.detallePedidoRepository.save(detallePedido);
	}
	

	public Iterable<DetallePedido> getAllDetallePedidos() {
		return this.detallePedidoRepository.findAll();
	}

	public Optional<DetallePedido> findDetallePedidoByID(Integer id) {
		return this.detallePedidoRepository.findById(id);
	}

	public void deleteDetallePedido(Integer id) {
		this.detallePedidoRepository.deleteById(id);
	}

}
