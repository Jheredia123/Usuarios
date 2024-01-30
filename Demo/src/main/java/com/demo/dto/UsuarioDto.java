package com.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class UsuarioDto {

    private String name;
    private String email;
    private String password;
    private List<TelefonoDto> phones;


}

