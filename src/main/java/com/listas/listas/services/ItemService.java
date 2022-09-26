package com.listas.listas.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.listas.listas.entities.ItemEntity;
import com.listas.listas.entities.ListaEntity;
import com.listas.listas.exceptions.NotFoundBussinessException;
import com.listas.listas.repositories.ItemRepository;

@Service
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Transactional
	public ItemEntity cria (ItemEntity itemEntity) {
		return itemRepository.save(itemEntity);
	}
	
	@Transactional
	public ItemEntity atualiza(ItemEntity itemEntity) {
		return itemRepository.save(itemEntity);
	}
	
	public ItemEntity buscaPorId(Long id) {
		Optional<ItemEntity> encontrou = itemRepository.findById(id);
			if(encontrou.isPresent()) {
				return encontrou.get();
			} else {
				throw new NotFoundBussinessException("Item" + id + "não foi encontrado");
			}
	}
	
	@Transactional
	public void remove(ItemEntity itemEntity) {
		itemRepository.delete(itemEntity);
	}
	
	public List<ItemEntity> listaItensPorLista(Long id) {
		return itemRepository.findByListaId(id);
	}
	
	@Transactional
	public void removeListaDeItens(List<ItemEntity> listaDeItens) {
		for (ItemEntity item : listaDeItens) {
			this.remove(item);
		}
	}

}
