package com.crisalis.app.dao;

import java.util.List;
import java.util.Optional;

import com.crisalis.app.model.Cliente;

public interface ClienteDao {
	
	List<Cliente> getClientes();

	void eliminar(Integer id);
	
	Cliente buscar(Integer id);

	void registrar(Cliente cliente);
	
	void editar(Cliente cliente);
	
}
