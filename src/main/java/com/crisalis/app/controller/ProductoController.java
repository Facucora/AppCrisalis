package com.crisalis.app.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.crisalis.app.model.Producto;
import com.crisalis.app.services.ProductoService;
import com.crisalis.app.utils.JWTUtil;



@RestController
public class ProductoController {
	
	@Autowired
	private final ProductoService productoService;
	
	public ProductoController(ProductoService productoService) {
		this.productoService = productoService;
	}
	
	@Autowired
    private JWTUtil jwtUtil;
	
	 private boolean validarToken(String token) {
	        String usuarioId = jwtUtil.getKey(token);
	        return usuarioId != null;
	 }

	@RequestMapping(value = "api/productos", method = RequestMethod.GET)
	public Iterable<Producto> getProductos(@RequestHeader(value="Authorization") String token) {
		if (!validarToken(token)) { return null; }
		return productoService.getAllProductos();
	}
	
	@RequestMapping(value = "api/productosimp", method = RequestMethod.GET)
	public Iterable<Producto> getProductosImp(@RequestHeader(value="Authorization") String token) {
		if (!validarToken(token)) { return null; }
		return productoService.getAllProductosImpuestos();
	}
	
	@RequestMapping(value = "api/productos/{id}", method = RequestMethod.GET)
	public Optional<Producto> getProductoId(@RequestHeader(value="Authorization") String token, @PathVariable Integer id) {
		if (!validarToken(token)) { return null; }
		return productoService.findProductoByID(id);
		
	}
	
	@RequestMapping(value = "api/productos/{id}", method = RequestMethod.DELETE)
	public void eliminar(@RequestHeader(value="Authorization") String token, @PathVariable Integer id) {
		 if (!validarToken(token)) { return; }
		 productoService.deleteProducto(id);
	}
	
	@RequestMapping(value = "api/productos", method = RequestMethod.POST)
	public void registrarProducto(@RequestBody Producto producto) {
		productoService.saveProducto(producto);
	}
	
	@RequestMapping(value = "api/productos/edit/{id}", method = RequestMethod.PUT)
	public void editarProducto(@PathVariable Integer id, @RequestBody Producto producto) {
		producto.setId(id);
		productoService.saveProducto(producto);
	}
}
