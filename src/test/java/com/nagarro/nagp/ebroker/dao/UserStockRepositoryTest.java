package com.nagarro.nagp.ebroker.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
public class UserStockRepositoryTest {

	@Autowired
	private UserStockRepository userStockRepo;
	
	// getFundsForTrader
		@Test
		void shouldTestEquityRepo() {
			UserStock us = new UserStock();
			Equity equity = new Equity();
			equity.setId(1);
			equity.setStockAmount(100);
			equity.setStockQuantity(1);
			User u = new User();
			u.setId(1L);
			u.setEmail("abc@a.com");
			u.setFirstName("Akash");
			u.setLastName("Ree");
			u.setPassword("#23e4");
			u.setPhoneNumber("987336636");
			Set<Fund> funds = new HashSet<>();
			Set<UserStock> equities = new HashSet<>();
			u.setFundss(funds);
			u.setEquities(equities);
			us.setEquity(equity);
			us.setId(1);
			us.setUser(u);
			us.setTotalAmount(50000);
			us.setQuantity(1);
			userStockRepo.save(us);
			List<UserStock> result = userStockRepo.getExistingUserStockForEquity(1,1L);
			Assertions.assertThat(result.get(0).getId()).isEqualTo(us.getId());
			
		}
}
