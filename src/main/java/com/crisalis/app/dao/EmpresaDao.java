package com.crisalis.app.dao;

import java.util.List;

import com.crisalis.app.model.Empresa;

public interface EmpresaDao {
	
	List<Empresa> getEmpresas();

	void eliminar(Integer id);

	void registrar(Empresa empresa);
	

}
