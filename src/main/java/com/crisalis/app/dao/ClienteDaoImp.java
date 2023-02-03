package com.crisalis.app.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.crisalis.app.model.Cliente;


@Repository
@Transactional
public class ClienteDaoImp implements ClienteDao {
	
	@PersistenceContext
	EntityManager entitymanager;
	
	@Override
	public List<Cliente> getClientes() {
		String query = "FROM Cliente";
		return entitymanager.createQuery(query, Cliente.class).getResultList();
	}

	@Override
	public void eliminar(Integer id) {
		Cliente cliente = entitymanager.find(Cliente.class, id);
				entitymanager.remove(cliente);
	}


	@Override
	public void registrar(Cliente cliente) {
		entitymanager.merge(cliente);
		
	}

	@Override
	public void editar(Cliente cliente) {
		entitymanager.merge(cliente);
		
	}
	
	@Override
	public Cliente buscar(Integer id) {
		String query = "FROM Cliente";
		return entitymanager.createQuery(query, Cliente.class).getSingleResult();
		
	}
	

}
