package com.demo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "User")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Telefono> phones;

	@Column(name = "created")
	private Date created;

	@Column(name = "modified")
	private Date modified;

	@Column(name = "lastLogin")
	private Date lastLogin;

	@Column(name = "token")
	private String token;

	@Column(name = "isActive")
	private boolean isActive;
}
