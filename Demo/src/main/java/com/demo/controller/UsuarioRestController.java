package com.demo.controller;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.UsuarioDto;
import com.demo.jwtConfig.JwtConfig;
import com.demo.response.UsuarioResponseRest;
import com.demo.services.IUsuarioService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class UsuarioRestController {

	@Autowired
	private IUsuarioService service;
	
    @Autowired
    private JwtConfig jwt;

	@GetMapping("/getAll")
	public ResponseEntity<UsuarioResponseRest> getAll() {
		return service.getAll();
	}

	@PostMapping("/saveUsuario")
	public ResponseEntity<UsuarioResponseRest> saveUsuario(
	    @RequestBody UsuarioDto usuario,
	    @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) String authorizationHeader
	) {
	    
	    return service.save(usuario, authorizationHeader);
	}
	
	
	//genera un nuevo token
	@PostMapping("user")
	public UsuarioDto login(@RequestParam("user") String username) {
		
		String token = generateJwtToken(username);
		UsuarioDto user = new UsuarioDto();
		user.setName(username);
		user.setPassword(token);		
		return user;
		
	}
	
	private String generateJwtToken(String username) {
	    // Genera una clave segura para HS256
	    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	    return Jwts.builder()
	            .setSubject(username)
	            .setIssuedAt(new Date())
	            .setExpiration(new Date(System.currentTimeMillis() + jwt.getExpirationTime()))
	            .signWith(key)
	            .compact();
	}
}
