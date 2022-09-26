package com.listas.listas.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.listas.listas.converts.ItemConvert;
import com.listas.listas.dtos.inputs.ItemInput;
import com.listas.listas.dtos.outputs.ItemOutput;
import com.listas.listas.entities.ItemEntity;
import com.listas.listas.entities.ListaEntity;
import com.listas.listas.services.ItemService;
import com.listas.listas.services.ListaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.bytebuddy.build.Plugin.Engine.Summary;

@Tag(name = "Item")
@RestController
@RequestMapping("/api/itens")
@CrossOrigin(origins = "*")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ItemConvert itemConvert;
	
	@Autowired
	private ListaService listaService;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@Operation(summary = "Cadastra item", description = "Cadastra um novo item no sistema")
	public ItemOutput cadastraItem(@Parameter(description = "Representação de um item") @RequestBody @Valid ItemInput itemInput) {
		ItemEntity itemEntity = itemConvert.inputToEntity(itemInput);
		ItemEntity itemCriado = itemService.cria(itemEntity);
		convertListas(itemInput, itemEntity);
		return itemConvert.entityToOutput(itemCriado);
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Altera item", description = "Altera as informações do item")
	public ItemOutput alteraItem(@Parameter(description = "Representação de um item") @RequestBody @Valid ItemInput itemInput,
		@Parameter(description = "Id do item", example = "1") @PathVariable Long id) {
		ItemEntity itemEntity = itemService.buscaPorId(id);
		itemConvert.copyInputToentity(itemInput, itemEntity);
		convertListas(itemInput, itemEntity);
		ItemEntity itemAlterado = itemService.atualiza(itemEntity);
		return itemConvert.entityToOutput(itemEntity);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@Operation(summary = "Exclui item", description = "Exclui o item pelo id")
	public void removeItem(@Parameter(description = "Id do item", example = "1") @PathVariable Long id) {
		ItemEntity itemEntity = itemService.buscaPorId(id);
		itemService.remove(itemEntity);
	}
	
	@PutMapping("/{id}/item-concluido")
	@Operation(summary = "Marca item como concluido", description = "Marca um item no sistema como concluido")
	public ItemOutput marcaItemComoConcluido(@Parameter(description = "Id do item", example = "1") @PathVariable Long id) {
		ItemEntity itemEntity = itemService.buscaPorId(id);
		ItemEntity itemAlterado = itemService.atualiza(itemEntity);
		itemEntity.setConcluido(true);
		return itemConvert.entityToOutput(itemAlterado);
	}
	
	@PutMapping("/{id}/item-nao-concluido")
	@Operation(summary = "Marca item como não concluido", description = "Marca um item no sistema como não concluido")
	public ItemOutput marcaItemComoNaoConcluido(@Parameter(description = "Id do item", example = "1") @PathVariable Long id) {
		ItemEntity itemEntity = itemService.buscaPorId(id);
		ItemEntity itemAlterado = itemService.atualiza(itemEntity);
		itemEntity.setConcluido(false);
		return itemConvert.entityToOutput(itemAlterado);
	}
	
	@GetMapping("/{idLista}/itens")
	@Operation(summary = "", description = "Lista todos os itens pelo id da lista")
	public List<ItemOutput> ListaItensPorListaId(@Parameter(description = "Id da lista", example = "1") @PathVariable Long listaId) {
		listaService.buscaPorId(listaId);
		List<ItemEntity> listaItens = itemService.listaItensPorLista(listaId);
		return itemConvert.entityToOutput(listaItens);
	}
	

	private void convertListas(ItemInput itemInput, ItemEntity itemEntity) {
		itemEntity.setLista(listaService.buscaPorId(itemInput.getListaId()));
	}

}
