package com.crisalis.app.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.crisalis.app.model.Cliente;
import com.crisalis.app.model.Pedido;


@Repository
@Transactional
public class PedidoDaoImp implements PedidoDao {
	
	@PersistenceContext
	EntityManager entitymanager;
	
	@Override
	public List<Pedido> getPedidos() {
		String query = "FROM Pedido";
		return entitymanager.createQuery(query, Pedido.class).getResultList();
	}

	@Override
	public void eliminar(Integer id) {
		Pedido pedido = entitymanager.find(Pedido.class, id);
				entitymanager.remove(pedido);
	}


	@Override
	public void registrar(Pedido pedido) {
		entitymanager.merge(pedido);
		
	}

	@Override
	public void editar(Pedido pedido) {
		entitymanager.merge(pedido);
		
	}
	
	@Override
	public Pedido buscar(Integer id) {
		String query = "FROM Pedido";
		return entitymanager.createQuery(query, Pedido.class).getSingleResult();
		
	}
	

}
