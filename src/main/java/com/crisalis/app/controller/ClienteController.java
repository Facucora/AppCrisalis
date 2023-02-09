package com.crisalis.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.crisalis.app.model.Cliente;
import com.crisalis.app.model.Producto;
import com.crisalis.app.services.ClienteService;
import com.crisalis.app.utils.JWTUtil;



@RestController
public class ClienteController {
	
	@Autowired
	private final ClienteService clienteService;
	
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@Autowired
    private JWTUtil jwtUtil;
	
	 private boolean validarToken(String token) {
	        String usuarioId = jwtUtil.getKey(token);
	        return usuarioId != null;
	 }

	@RequestMapping(value = "api/clientes", method = RequestMethod.GET)
	public Iterable<Cliente> getClientes(@RequestHeader(value="Authorization") String token) {
		if (!validarToken(token)) { return null; }
		return clienteService.getAllClientes();
	}
	
	@RequestMapping(value = "api/clientes/{id}", method = RequestMethod.GET)
	public Optional<Cliente> getClienteId(@RequestHeader(value="Authorization") String token, @PathVariable Integer id) {
		if (!validarToken(token)) { return null; }
		return clienteService.findClienteByID(id);
		
	}
	
	@RequestMapping(value = "api/clientes/{id}", method = RequestMethod.DELETE)
	public void eliminar(@RequestHeader(value="Authorization") String token, @PathVariable Integer id) {
		 if (!validarToken(token)) { return; }
		 clienteService.deleteCliente(id);
	}
	
	@RequestMapping(value = "api/clientes", method = RequestMethod.POST)
	public void registrarUsuarios(@RequestBody Cliente cliente) {
		clienteService.saveCliente(cliente);
	}
	
	@RequestMapping(value = "api/clientes/productos/{id}", method = RequestMethod.GET)
	public Iterable<Producto> getServiciosContratados(@PathVariable Integer id) {
	        List<Producto> productosContratados = clienteService.getServiciosContratados(id);
	        return productosContratados;
	}
	
	@RequestMapping(value = "api/clientes/contratados/{id}", method = RequestMethod.GET)
	public Iterable<Producto> getContratados(@PathVariable Integer id) {
	        List<Producto> productosContratados = clienteService.getProductosContratados(id);
	        return productosContratados;
	}
	@RequestMapping(value = "api/clientes/edit/{id}", method = RequestMethod.PUT)
	public void editarCliente(@PathVariable Integer id, @RequestBody Cliente cliente) {
		cliente.setId(id);
		clienteService.saveCliente(cliente);		
	}
}
