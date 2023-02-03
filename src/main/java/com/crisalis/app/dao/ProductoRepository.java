package com.crisalis.app.dao;

import org.springframework.data.repository.CrudRepository;

import com.crisalis.app.model.Cliente;
import com.crisalis.app.model.Producto;

public interface ProductoRepository extends CrudRepository<Producto, Integer> {
	
	

}

