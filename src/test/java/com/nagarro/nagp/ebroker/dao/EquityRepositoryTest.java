package com.nagarro.nagp.ebroker.dao;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.nagarro.nagp.ebroker.model.Equity;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class EquityRepositoryTest {

	@Autowired
	EquityRepository equityRepo;
	
	@Test
	void shouldTestEquityRepo() {
		Equity equity = new Equity();
		equity.setId(1);
		equity.setStockAmount(100);
		equity.setStockQuantity(1);
		equityRepo.save(equity);
		Equity result = equityRepo.getEquityById(1);
		Assertions.assertThat(result.getId()).isEqualTo(equity.getId());
		equityRepo.deleteAll();
		Assertions.assertThat(equityRepo.findAll()).isEmpty();
	}
}
