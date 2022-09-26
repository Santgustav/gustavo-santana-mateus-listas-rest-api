package com.listas.listas.converts;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.listas.listas.dtos.inputs.ListaInput;
import com.listas.listas.dtos.outputs.ListaOutput;
import com.listas.listas.entities.ListaEntity;

@Component
public class ListaConvert {

	@Autowired
	private ModelMapper modelMapper;
	
	public List<ListaOutput> entityToOutput(List<ListaEntity> listasEntity) {
		return listasEntity.stream().map(this::entityToOutput).collect(Collectors.toList());
	}
	
	public ListaOutput entityToOutput(ListaEntity listaEntity) {
		return modelMapper.map(listaEntity, ListaOutput.class);
	}
	
	public ListaEntity inputToEntity(ListaInput listaInput) {
		return modelMapper.map(listaInput, ListaEntity.class);
	}
	
	public void copyInputToEntity(ListaInput listaInput, ListaEntity listaEntity) {
		modelMapper.map(listaInput, listaEntity);
	}
}
