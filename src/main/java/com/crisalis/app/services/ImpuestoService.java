package com.crisalis.app.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crisalis.app.dao.ImpuestosRepository;
import com.crisalis.app.model.Impuestos;

@Service
public class ImpuestoService {
	
	@Autowired
	private final ImpuestosRepository impuestosRepository;
	
	public ImpuestoService(ImpuestosRepository impuestosRepository) {
		this.impuestosRepository = impuestosRepository;
	}

	public Impuestos saveImpuestos(Impuestos impuestos) {
			return this.impuestosRepository.save(impuestos);
	}
	

	public Iterable<Impuestos> getAllImpuestos() {
		return this.impuestosRepository.findAll();
		
	}

	public Optional<Impuestos> findImpuestosByID(Integer id) {
		return this.impuestosRepository.findById(id);
	}

	public void deletePedido(Integer id) {
		this.impuestosRepository.deleteById(id);
	}
	

}
