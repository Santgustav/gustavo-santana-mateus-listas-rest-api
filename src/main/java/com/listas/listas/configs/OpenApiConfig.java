package com.listas.listas.configs;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class OpenApiConfig {
	
	public OpenAPI springShowOpenApi() {
		
		OpenAPI openApi = new OpenAPI();
		
		Info info = new Info();
		
		info.title("Acelera G&P Avaliação 03 Gustavo Santana Mateus");
		info.version("v0.0.1");
		info.description("<h3> Uma aplicação de Listas para avaliação 03 </h3>");
		openApi.info(info);
		
		openApi.addTagsItem(new Tag().name("Item").description("Gerencia os itens do sistema"));
		openApi.addTagsItem(new Tag().name("Lista").description("Gerencia as Listas do sistema"));
		
		return openApi;
	}

}
