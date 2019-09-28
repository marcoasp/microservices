package br.com.caelum.eatsdistanciaservice;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

/*
 * Serviço que simula a obtenção dos restaurantes mais próximos a um dado CEP.
 * Deve evoluir para uma solução que utiliza geolocalização.
 *
 */
@Service
@AllArgsConstructor
class DistanciaService {

	private static final Pageable LIMIT = PageRequest.of(0,5);

	private final RestauranteMongoRepository restaurantes;

	List<RestauranteComDistanciaDto> restaurantesMaisProximosAoCep(String cep) {
		final List<RestauranteMongo> aprovados = restaurantes.findAll(LIMIT).getContent();
		return calculaDistanciaParaOsRestaurantes(aprovados, cep);
	}

	List<RestauranteComDistanciaDto> restaurantesDoTipoDeCozinhaMaisProximosAoCep(Long tipoDeCozinhaId, String cep) {
		final List<RestauranteMongo> aprovadosDoTipoDeCozinha = restaurantes.findAllByTipoDeCozinhaId(tipoDeCozinhaId, LIMIT).getContent();
		return calculaDistanciaParaOsRestaurantes(aprovadosDoTipoDeCozinha, cep);
	}

	RestauranteComDistanciaDto restauranteComDistanciaDoCep(Long restauranteId, String cep) {
		final RestauranteMongo restaurante = restaurantes.findById(restauranteId).orElseThrow(() -> new ResourceNotFoundException());
		final String cepDoRestaurante = restaurante.getCep();
		final BigDecimal distancia = distanciaDoCep(cepDoRestaurante, cep);
		return new RestauranteComDistanciaDto(restauranteId, distancia);
	}

	private List<RestauranteComDistanciaDto> calculaDistanciaParaOsRestaurantes(List<RestauranteMongo> aprovados, String cep) {
		return aprovados
				.stream()
				.map(restaurante -> {
					final String cepDoRestaurante = restaurante.getCep();
					final BigDecimal distancia = distanciaDoCep(cepDoRestaurante, cep);
					final Long restauranteId = restaurante.getId();
					return new RestauranteComDistanciaDto(restauranteId, distancia);
				})
				.collect(Collectors.toList());
	}

	private BigDecimal distanciaDoCep(String cepDoRestaurante, String cep) {
		return calculaDistancia();
	}

	private BigDecimal calculaDistancia() {
		//simulaDemora();
		return new BigDecimal(Math.random()*15);
	}

	@SuppressWarnings("unused")
	private void simulaDemora() {
		//simula demora de 10s a 20s
		final long demora = (long) (Math.random()*10000+10000);
		try {
			Thread.sleep(demora);
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
