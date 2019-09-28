package br.com.caelum.eatsdistanciaservice;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Document(collection = "restaurantes")
@Data
@AllArgsConstructor
public class RestauranteMongo {

	@Id
	private Long id;
	
	private String cep;
	
	private Long tipoDeCozinhaId;
}
