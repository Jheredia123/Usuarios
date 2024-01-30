package com.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.Usuario;

public interface IUsuarioDao extends JpaRepository<Usuario, Long> {

}
