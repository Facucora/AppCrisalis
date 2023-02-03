package com.crisalis.app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.crisalis.app.model.Producto;


@Repository
@Transactional
public class ProductoDaoImp implements ProductoDao {
	
	@PersistenceContext
	EntityManager entitymanager;
	
	@Override
	public List<Producto> getProductos() {
		String query = "FROM Producto";
		return entitymanager.createQuery(query, Producto.class).getResultList();
	}

	@Override
	public void eliminar(Integer id) {
		Producto producto = entitymanager.find(Producto.class, id);
				entitymanager.remove(producto);
	}


	@Override
	public void registrar(Producto producto) {
		entitymanager.merge(producto);
		
	}
	

}
