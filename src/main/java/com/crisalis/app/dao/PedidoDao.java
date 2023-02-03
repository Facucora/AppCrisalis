package com.crisalis.app.dao;

import java.util.List;
import java.util.Optional;

import com.crisalis.app.model.Cliente;
import com.crisalis.app.model.Pedido;

public interface PedidoDao {
	
	List<Pedido> getPedidos();

	void eliminar(Integer id);
	
	Pedido buscar(Integer id);

	void registrar(Pedido pedido);
	
	void editar(Pedido pedido);
	
}
