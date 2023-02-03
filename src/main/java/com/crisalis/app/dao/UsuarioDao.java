package com.crisalis.app.dao;

import java.util.List;

import com.crisalis.app.model.Usuario;

public interface UsuarioDao {
	
	List<Usuario> getUsuarios();

	void eliminar(Integer id);

	void registrar(Usuario usuario);
	
	Usuario verificar(Usuario usuario);

}
