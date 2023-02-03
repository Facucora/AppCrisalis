package com.crisalis.app.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.crisalis.app.model.Impuestos;
import com.crisalis.app.services.ImpuestoService;
import com.crisalis.app.utils.JWTUtil;

@RestController
public class ImpuestosController {
	
	@Autowired
	private final ImpuestoService impuestoService;
	
	public ImpuestosController(ImpuestoService impuestoService) {
		this.impuestoService = impuestoService;
	}
	
	
	@Autowired
    private JWTUtil jwtUtil;
	
	 private boolean validarToken(String token) {
	        String usuarioId = jwtUtil.getKey(token);
	        return usuarioId != null;
	 }

	@RequestMapping(value = "api/impuestos", method = RequestMethod.GET)
	public Iterable<Impuestos> getImpuestos(@RequestHeader(value="Authorization") String token) {
		if (!validarToken(token)) { return null; }
		return impuestoService.getAllImpuestos();
	}
	
	
	@RequestMapping(value = "api/impuestos/{id}", method = RequestMethod.GET)
	public Optional<Impuestos> getClienteId(@RequestHeader(value="Authorization") String token, @PathVariable Integer id) {
		if (!validarToken(token)) { return null; }
		return impuestoService.findImpuestosByID(id);
		
	}
	
	@RequestMapping(value = "api/impuestos/{id}", method = RequestMethod.DELETE)
	public void eliminar(@RequestHeader(value="Authorization") String token, @PathVariable Integer id) {
		 if (!validarToken(token)) { return; }
		 impuestoService.deletePedido(id);
	}
	
	@RequestMapping(value = "api/impuestos", method = RequestMethod.POST)
	public void registrarImpuesto(@RequestBody Impuestos impuesto) {
		impuestoService.saveImpuestos(impuesto);
	}
}
