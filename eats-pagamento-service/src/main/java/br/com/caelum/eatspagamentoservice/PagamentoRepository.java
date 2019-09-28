package br.com.caelum.eatspagamentoservice;

import org.springframework.data.jpa.repository.JpaRepository;

interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

}
