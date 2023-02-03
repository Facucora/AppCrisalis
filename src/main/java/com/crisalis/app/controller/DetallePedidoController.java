package com.crisalis.app.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.crisalis.app.model.DetallePedido;
import com.crisalis.app.services.DetallePedidoService;
import com.crisalis.app.utils.JWTUtil;



@RestController
public class DetallePedidoController {
	
	@Autowired
	private final DetallePedidoService detallePedidoService;
	
	public DetallePedidoController(DetallePedidoService detallePedidoService) {
		this.detallePedidoService = detallePedidoService;
	}
	
	@Autowired
    private JWTUtil jwtUtil;
	
	 private boolean validarToken(String token) {
	        String usuarioId = jwtUtil.getKey(token);
	        return usuarioId != null;
	 }

	@RequestMapping(value = "api/detallepedido", method = RequestMethod.GET)
	public Iterable<DetallePedido> getDetallePedido(@RequestHeader(value="Authorization") String token) {
		if (!validarToken(token)) { return null; }
		return detallePedidoService.getAllDetallePedidos();
	}
	
	@RequestMapping(value = "api/detallepedido/{id}", method = RequestMethod.GET)
	public Optional<DetallePedido> getDetallePedidoId(@RequestHeader(value="Authorization") String token, @PathVariable Integer id) {
		if (!validarToken(token)) { return null; }
		return detallePedidoService.findDetallePedidoByID(id);
		
	}
	
	@RequestMapping(value = "api/detallepedido/{id}", method = RequestMethod.DELETE)
	public void eliminarDetallePedido(@RequestHeader(value="Authorization") String token, @PathVariable Integer id) {
		 if (!validarToken(token)) { return; }
		 detallePedidoService.deleteDetallePedido(id);
	}
	
	@RequestMapping(value = "api/detallepedido", method = RequestMethod.POST)
	public void registrarDetallePedido(@RequestBody DetallePedido detallepedido) {
		detallePedidoService.saveDetallePedido(detallepedido);
	}
	
	@RequestMapping(value = "api/detallepedido/edit/{id}", method = RequestMethod.PUT)
	public void editarDetailOrder(@PathVariable Integer id, @RequestBody DetallePedido detallepedido) {
		detallepedido.setId(id);
		detallePedidoService.saveDetallePedido(detallepedido);
		
	}
}
