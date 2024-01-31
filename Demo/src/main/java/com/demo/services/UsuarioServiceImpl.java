package com.demo.services;

import java.security.Key;
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
import com.demo.utils.EmailFormatException;
import com.demo.utils.EmailValidator;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

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
			if (usuario.size() > 0) {
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
	public ResponseEntity<UsuarioResponseRest> save(UsuarioDto usuario, String authorizationHeader) {
		UsuarioResponseRest response = new UsuarioResponseRest();
		List<Usuario> list = new ArrayList<>();
		try {
			// Validar el formato del correo electrónico
			if (!EmailValidator.isValidEmail(usuario.getEmail())) {
				throw new EmailFormatException("Formato de correo electrónico inválido");
			}
			// Verificar si el correo ya está registrado
			List<Usuario> usuariosBd = usuarioDao.findAll();
			Usuario usuarioEntity = new Usuario();
			if (usuariosBd.size() > 0) {					
				for (Usuario usu : usuariosBd) {
					if (usuario.getName().equals(usu.getName())) {
						// usuario ya creado
						usuarioEntity.setLastLogin(
								Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
						usuarioEntity
								.setModified(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
						usuarioEntity.setToken(authorizationHeader);
						usuarioEntity.setActive(true);
						usuarioEntity = usuarioDao.save(usuarioEntity);
					} 
				
				}
				list.add(usuarioEntity);
				response.getUsuarioResponse().setUsuario(list);
				response.setMetadata("Insercion correcta", "00", "Usuario insertado");
			} else {
				// usuario Nuevo
				usuarioEntity.setModified(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
				usuarioEntity.setToken(authorizationHeader);
				usuarioEntity.setActive(true);
				usuarioEntity.setLastLogin(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
				usuarioEntity.setCreated(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
				usuarioEntity = modelMapper.map(usuario, Usuario.class);

				usuarioEntity = usuarioDao.save(usuarioEntity);
				list.add(usuarioEntity);
				response.getUsuarioResponse().setUsuario(list);
				response.setMetadata("Insercion correcta", "00", "Usuario insertado");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	private String generateJwtToken(String username) {
		// Genera una clave segura para HS256
		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + jwt.getExpirationTime())).signWith(key).compact();
	}

}
