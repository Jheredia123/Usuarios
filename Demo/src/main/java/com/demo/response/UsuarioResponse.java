package com.demo.response;

import java.util.List;

import com.demo.model.Usuario;

import lombok.Data;

@Data
public class UsuarioResponse {

	private List<Usuario> usuario;

}
