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

import com.nagarro.nagp.ebroker.model.Fund;
import com.nagarro.nagp.ebroker.model.User;
import com.nagarro.nagp.ebroker.model.UserStock;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {


	@Autowired
	UserRepository userRepo;

	// getFundsForTrader
	@Test
	void shouldTestEquityRepo() {
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
		userRepo.save(u);
		Optional<User> result = userRepo.findById(1L);
		Assertions.assertThat(result.get().getId()).isEqualTo(u.getId());
		
	}
}
