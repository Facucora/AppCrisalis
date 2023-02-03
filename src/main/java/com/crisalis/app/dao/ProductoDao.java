package com.crisalis.app.dao;

import java.util.List;

import com.crisalis.app.model.Producto;

public interface ProductoDao {
	
	List<Producto> getProductos();

	void eliminar(Integer id);

	void registrar(Producto producto);
	
}
