package com.crisalis.app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.crisalis.app.model.Usuario;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@Repository
@Transactional
public class UsuarioDaoImp implements UsuarioDao {
	
	@PersistenceContext
	EntityManager entitymanager;
	
	@Override
	public List<Usuario> getUsuarios() {
		String query = "FROM Usuario";
		return entitymanager.createQuery(query, Usuario.class).getResultList();
	}

	@Override
	public void eliminar(Integer id) {
		Usuario usuario = entitymanager.find(Usuario.class, id);
				entitymanager.remove(usuario);
	}


	@Override
	public void registrar(Usuario usuario) {
		entitymanager.merge(usuario);
		
	}
	@Override
	public Usuario verificar(Usuario usuario) {
		String query = "FROM Usuario WHERE mail = :mail";
		List<Usuario> lista = entitymanager.createQuery(query)
                .setParameter("mail", usuario.getMail())
                .getResultList(); 
		
		if (lista.isEmpty()) {
		       return null;
	    }  
	        String passwordHashed = lista.get(0).getPassword();
			System.out.println(passwordHashed);
			System.out.println(usuario.getPassword());
			Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
	        if (argon2.verify(passwordHashed, usuario.getPassword())) {
	            return lista.get(0);
	        }
	        return null;
	}

}
