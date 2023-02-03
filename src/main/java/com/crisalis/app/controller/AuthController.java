package com.crisalis.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.crisalis.app.dao.UsuarioDao;
import com.crisalis.app.model.Usuario;
import com.crisalis.app.utils.JWTUtil;

@RestController
public class AuthController {
	
	@Autowired
	private UsuarioDao usuarioDao;
	

    @Autowired
    private JWTUtil jwtUtil;
	
	@RequestMapping(value = "api/login", method = RequestMethod.POST)
	public String login(@RequestBody Usuario usuario) {
		Usuario usuarioLogueado = usuarioDao.verificar(usuario);
		
		 if (usuarioLogueado != null) {
	            String tokenJwt = jwtUtil.create(String.valueOf(usuarioLogueado.getId()), usuarioLogueado.getMail());
	            return tokenJwt;
	        }
	        return "FAIL";
	}

}
