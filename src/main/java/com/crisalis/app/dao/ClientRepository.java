package com.crisalis.app.dao;

import org.springframework.data.repository.CrudRepository;

import com.crisalis.app.model.Cliente;

public interface ClientRepository extends CrudRepository<Cliente, Integer> {
	
	

}

