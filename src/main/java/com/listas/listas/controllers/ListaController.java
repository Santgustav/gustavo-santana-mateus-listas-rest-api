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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.listas.listas.converts.ItemConvert;
import com.listas.listas.converts.ListaConvert;
import com.listas.listas.dtos.inputs.ListaInput;
import com.listas.listas.dtos.outputs.ItemOutput;
import com.listas.listas.dtos.outputs.ListaOutput;
import com.listas.listas.entities.ItemEntity;
import com.listas.listas.entities.ListaEntity;
import com.listas.listas.services.ItemService;
import com.listas.listas.services.ListaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Lista")
@RestController
@RequestMapping("/api/listas")
@CrossOrigin(origins = "*")
public class ListaController {
	
	@Autowired
	private ListaConvert listaConvert;
	
	@Autowired
	private ListaService listaService;
	
	@Autowired 
	private ItemService itemService;
	
	@Autowired
	private ItemConvert itenConvert;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@Operation(summary = "Cadastra lista", description = "Cadastra uma nova lista")
	public ListaOutput cadastraLista(@Parameter(description = "Representação de uma lista") @RequestBody @Valid ListaInput lista) {
		ListaEntity listaEntity = listaConvert.inputToEntity(lista);
		ListaEntity listaCriada = listaService.cria(listaEntity);
		return listaConvert.entityToOutput(listaCriada);
	}
	
	public ListaOutput alteraLista(@Parameter(description = "Id da list", example = "1") @RequestBody @Valid ListaInput listaInput,
		@Parameter(description = "Representação de uma lista") @PathVariable Long id) {
		ListaEntity listaEntity = listaService.buscaPorId(id);
		ListaEntity listaAlterada = listaService.atualiza(listaEntity);
		listaConvert.copyInputToEntity(listaInput, listaEntity);
		return listaConvert.entityToOutput(listaAlterada);
	}
	
	@GetMapping
	@Operation(summary = "Lista as listas", description = "Lista todas as listas cadastradas")
	public List<ListaOutput> listaTodasListas() {
		List<ListaEntity> listas = listaService.listaTodos();
		return listaConvert.entityToOutput(listas);
	}
	
	@GetMapping("/lista/{id}")
	@Operation(summary = "Busca lista por Id", description = "Busca uma lista pelo id da lista")
	public ListaOutput buscaListaPorId(@Parameter(description = "Id da lista", example = "1") @PathVariable Long id) {
		ListaEntity listaEntity = listaService.buscaPorId(id);
		return listaConvert.entityToOutput(listaEntity);
	}
	
	
	@DeleteMapping
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@Operation(summary = "Exclui lista", description = "Exclui uma lista pelo id")
	public void removeLista(@Parameter(description = "Id da lista", example = "1") @PathVariable Long id) {
		ListaEntity listaEntity = listaService.buscaPorId(id);
		List<ItemEntity> listaDeItens = itemService.listaItensPorLista(id);
		itemService.removeListaDeItens(listaDeItens);
		listaService.remove(listaEntity);
	}

}
