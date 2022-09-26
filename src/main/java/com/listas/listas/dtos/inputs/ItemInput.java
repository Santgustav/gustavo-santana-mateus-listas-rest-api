package com.listas.listas.dtos.inputs;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemInput {
	
	@Length(message= "O Título deve conter no máximo 100 caracteres!" , min = 1, max = 100)
	@NotEmpty(message = "O Título é obrigatório!")
	private String titulo;
	
	@NotNull(message = "Lista obrigatória!")
	private Long listaId;

}
