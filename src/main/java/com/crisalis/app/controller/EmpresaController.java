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


import com.crisalis.app.model.Empresa;
import com.crisalis.app.model.Producto;
import com.crisalis.app.services.EmpresaService;
import com.crisalis.app.utils.JWTUtil;



@RestController
public class EmpresaController {
	

	@Autowired
	private final EmpresaService empresaService;
	
	public EmpresaController(EmpresaService empresaService) {
		this.empresaService = empresaService;
	}
	@Autowired
    private JWTUtil jwtUtil;
	
	 private boolean validarToken(String token) {
	        String usuarioId = jwtUtil.getKey(token);
	        return usuarioId != null;
	 }

	@RequestMapping(value = "api/empresas", method = RequestMethod.GET)
	public Iterable<Empresa> getEmpresas(@RequestHeader(value="Authorization") String token) {
		if (!validarToken(token)) { return null; }
		return empresaService.getAllEmpresa();
	}
	
	@RequestMapping(value = "api/empresas/{id}", method = RequestMethod.GET)
	public Optional<Empresa> getEmpresaId(@RequestHeader(value="Authorization") String token, @PathVariable Integer id) {
		if (!validarToken(token)) { return null; }
		return empresaService.findEmpresaByID(id);
		
	}
	
	@RequestMapping(value = "api/empresas/{id}", method = RequestMethod.DELETE)
	public void eliminar(@RequestHeader(value="Authorization") String token, @PathVariable Integer id) {
		 if (!validarToken(token)) { return; }
		 empresaService.deleteEmpresa(id);
	}
	
	@RequestMapping(value = "api/empresas", method = RequestMethod.POST)
	public void registrarEmpresa(@RequestBody Empresa empresa) {
		empresaService.saveEmpresa(empresa);
	}
	
	@RequestMapping(value = "api/empresas/productos/{id}", method = RequestMethod.GET)
	public Iterable<Producto> getServiciosContratados(@PathVariable Integer id) {
	        List<Producto> productosContratados = empresaService.getServiciosContratados(id);
	        return productosContratados;
	}
	
	@RequestMapping(value = "api/empresas/edit/{id}", method = RequestMethod.PUT)
	public void editarCliente(@PathVariable Integer id, @RequestBody Empresa empresa) {
		empresa.setId(id);
		empresaService.saveEmpresa(empresa);		
	}
}
