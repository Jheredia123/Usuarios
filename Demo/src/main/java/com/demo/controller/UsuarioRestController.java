package com.demo.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.UsuarioDto;
import com.demo.jwtConfig.JwtConfig;
import com.demo.model.Usuario;
import com.demo.response.UsuarioResponseRest;
import com.demo.services.IUsuarioService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class UsuarioRestController {

	@Autowired
	private IUsuarioService service;

	@Autowired
	private JwtConfig jwtConfig;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Usuario usuario) {

		String token = generateJwtToken(usuario.getToken());
		return ResponseEntity.ok(token);
	}


	private String generateJwtToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpirationTime()))
				.signWith(SignatureAlgorithm.HS256, jwtConfig.getSecretKey()).compact();
	}

	@GetMapping("/getAll")
	public ResponseEntity<UsuarioResponseRest> getAll() {

		return service.getAll();
	}

	@PostMapping("/saveUsuario")
	public ResponseEntity<UsuarioResponseRest> saveUsuario(
	    @RequestBody UsuarioDto usuario,
	    @RequestHeader("Authorization") String authorizationHeader
	) {
	 
	    return service.save(usuario);
	}



	
	
}
