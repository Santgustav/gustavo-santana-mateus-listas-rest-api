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
import com.listas.listas.repositories.ListaRepository;

@Service
public class ListaService {
	
	@Autowired
	private ListaRepository listaRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Transactional
	public ListaEntity cria(ListaEntity listaEntity) {
		return listaRepository.save(listaEntity);
	}
	
	@Transactional
	public ListaEntity atualiza(ListaEntity listaEntity) {
		return listaRepository.save(listaEntity);
	}
	
	public List<ListaEntity> listaTodos() {
		return listaRepository.findAll();
	}
	
	public ListaEntity buscaPorId(Long id) {
		Optional<ListaEntity> encontrou = listaRepository.findById(id);
			if(encontrou.isPresent()) {
				return encontrou.get();
			} else {
				throw new NotFoundBussinessException("Lista" + id + "não foi encontrada");
			}
	}
	
	@Transactional
	public void remove(ListaEntity listaEntity) {
		List<ItemEntity> itens = itemRepository.findByListaId(listaEntity.getId());
			if(!itens.isEmpty()) {
				itens.forEach(item -> {
					itemRepository.delete(item);
				});
			}
			
			listaRepository.delete(listaEntity);
	}
}
