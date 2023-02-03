package com.crisalis.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.crisalis.app.model.Cliente;
import com.crisalis.app.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
	
	List<Pedido> findByClienteId(Integer clienteId);

}
