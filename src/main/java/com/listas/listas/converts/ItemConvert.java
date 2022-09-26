package com.listas.listas.converts;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.listas.listas.dtos.inputs.ItemInput;
import com.listas.listas.dtos.outputs.ItemOutput;
import com.listas.listas.entities.ItemEntity;

@Component
public class ItemConvert {

	@Autowired
	private ModelMapper modelMapper;
	
	public List<ItemOutput> entityToOutput(List<ItemEntity> itensEntity) {
		return itensEntity.stream().map(this::entityToOutput).collect(Collectors.toList());
	}
	
	public ItemOutput entityToOutput(ItemEntity itemEntity) {
		return modelMapper.map(itemEntity, ItemOutput.class);
	}
	
	public ItemEntity inputToEntity(ItemInput itemInput) {
		return modelMapper.map(itemInput, ItemEntity.class);
	}
	
	public void copyInputToentity(ItemInput itemInput, ItemEntity itemEntity) {
		modelMapper.map(itemInput, itemEntity);
	}
}
