package com.crisalis.app.dao;

import org.springframework.data.repository.CrudRepository;

import com.crisalis.app.model.Cliente;
import com.crisalis.app.model.Empresa;

public interface EmpresaRepository extends CrudRepository<Empresa, Integer> {
	
	

}

