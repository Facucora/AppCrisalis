package com.crisalis.app.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crisalis.app.dao.EmpresaRepository;
import com.crisalis.app.model.Empresa;

@Service
public class EmpresaService {
	
	@Autowired
	private final EmpresaRepository empresaRepository;
	@Autowired
	private ClienteService clienteService;
	
	public EmpresaService(EmpresaRepository empresaRepository) {
		this.empresaRepository = empresaRepository;
	}

	public Empresa saveEmpresa(Empresa empresa) {
			return this.empresaRepository.save(empresa);
	}
	

	public Iterable<Empresa> getAllEmpresa() {
		return this.empresaRepository.findAll();
	}

	public Optional<Empresa> findEmpresaByID(Integer id) {
		return this.empresaRepository.findById(id);
	}

	public void deleteEmpresa(Integer id) {
		this.empresaRepository.deleteById(id);
	}

}
