package com.demo.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.IUsuarioDao;
import com.demo.dto.UsuarioDto;
import com.demo.jwtConfig.JwtConfig;
import com.demo.model.Usuario;
import com.demo.response.UsuarioResponseRest;
import com.demo.utils.DuplicateEmailException;
import com.demo.utils.EmailFormatException;
import com.demo.utils.EmailValidator;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private IUsuarioDao usuarioDao;
	

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private JwtConfig jwt;


	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<UsuarioResponseRest> getAll() {

		UsuarioResponseRest response = new UsuarioResponseRest();

		try {
			List<Usuario> usuario = usuarioDao.findAll();
			if (!usuario.isEmpty()) {
				response.getUsuarioResponse().setUsuario(usuario);
				response.setMetadata("Respuesta ok ", "00", "Respuesta exitosa");
				System.out.println(response.getMetadata());
			} else {
				response.setMetadata("mensaje : ", "00", "no existen usuarios");
				System.out.println(response.getMetadata());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<UsuarioResponseRest> save(UsuarioDto usuario) {
		UsuarioResponseRest response = new UsuarioResponseRest();
		List<Usuario> list = new ArrayList<>();

		try {
			// Validar el formato del correo electrónico
			if (!EmailValidator.isValidEmail(usuario.getEmail())) {
				throw new EmailFormatException("Formato de correo electrónico inválido");
			}
			// Verificar si el correo ya está registrado
			List<Usuario> usuariosBd = usuarioDao.findAll();
			for (Usuario usu : usuariosBd) {
				if (usuario.getEmail().equals(usu.getEmail())) {
					throw new DuplicateEmailException("El correo ya registrado");
				}
			}
	
			String token = generateJwtToken(usuario.getEmail());
			Usuario usuarioEntity = modelMapper.map(usuario, Usuario.class);
			
			usuarioEntity.setCreated(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
			usuarioEntity.setLastLogin(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
			usuarioEntity.setModified(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
		
			usuarioEntity.setToken(token);
			usuarioEntity.setActive(true);

			usuarioEntity = usuarioDao.save(usuarioEntity);


			list.add(usuarioEntity);
			response.getUsuarioResponse().setUsuario(list);
			response.setMetadata("Insercion correcta", "00", "Usuario insertado");

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	private String generateJwtToken(String username) {
	    return Jwts.builder()
	            .setSubject(username)
	            .setIssuedAt(new Date())
	            .signWith(SignatureAlgorithm.HS256, "tuClaveSecreta") 
	            .compact();
	}
	
	
}
