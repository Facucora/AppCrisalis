package com.crisalis.app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.crisalis.app.model.Empresa;


@Repository
@Transactional
public class EmpresaDaoImp implements EmpresaDao {
	
	@PersistenceContext
	EntityManager entitymanager;
	
	@Override
	public List<Empresa> getEmpresas() {
		String query = "FROM Empresa";
		return entitymanager.createQuery(query, Empresa.class).getResultList();
	}

	@Override
	public void eliminar(Integer id) {
		Empresa empresa = entitymanager.find(Empresa.class, id);
				entitymanager.remove(empresa);
	}


	@Override
	public void registrar(Empresa empresa) {
		entitymanager.merge(empresa);
		
	}
	

}
