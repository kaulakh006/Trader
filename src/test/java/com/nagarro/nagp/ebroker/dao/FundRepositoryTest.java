package com.nagarro.nagp.ebroker.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.nagarro.nagp.ebroker.model.Equity;
import com.nagarro.nagp.ebroker.model.Fund;
import com.nagarro.nagp.ebroker.model.User;
import com.nagarro.nagp.ebroker.model.UserStock;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class FundRepositoryTest {

	@Autowired
	FundRepository fundRepo;

	// getFundsForTrader
	@Test
	void shouldTestFundRepo() {
		Fund fund = new Fund();
		fund.setAmount(1000.0d);
		fund.setId(10);
		User u = new User();
		u.setEmail("abc@a.com");
		u.setFirstName("Akash");
		u.setLastName("Ree");
		u.setPassword("#23e4");
		u.setPhoneNumber("987336636");
		Set<Fund> funds = new HashSet<>();
		Set<UserStock> equities = new HashSet<>();
		u.setFundss(funds);
		u.setEquities(equities);
		u.setId(10L);
		fund.setOwner(u);
		fundRepo.save(fund);
		List<Fund> result = fundRepo.getFundsForTrader(10L);
		for(Fund f : result) {
			Assertions.assertThat(f.getId()).isEqualTo(fund.getId());
			fundRepo.deleteAll();
			Assertions.assertThat(fundRepo.findAll()).isEmpty();
		}
		
	}
}
