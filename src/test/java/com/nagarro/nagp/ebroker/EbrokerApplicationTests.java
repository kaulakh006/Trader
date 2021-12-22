package com.nagarro.nagp.ebroker;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.nagarro.nagp.ebroker.controller.EbrokerController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EbrokerApplicationTests {

	@Autowired
	EbrokerController ebrokerController;
	
	@Test
	void contextLoads() {
		Assertions.assertThat(ebrokerController).isNotNull();
	}

}
