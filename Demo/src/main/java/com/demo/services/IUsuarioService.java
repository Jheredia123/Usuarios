package com.demo.services;

import org.springframework.http.ResponseEntity;

import com.demo.dto.UsuarioDto;
import com.demo.model.Usuario;
import com.demo.response.UsuarioResponseRest;

public interface IUsuarioService {

	ResponseEntity<UsuarioResponseRest> getAll();

	ResponseEntity<UsuarioResponseRest> save(UsuarioDto usuario);

}
