package com.nagarro.nagp.ebroker.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.nagarro.nagp.ebroker.service.EbrokerService;
import com.nagarro.nagp.ebroker.util.DateUtil;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EbrokerController.class)
public class EbrokerControllerTest {

	@MockBean
	private EbrokerService ebrokerService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void shouldTestCheckFundForTrader_successCase() throws Exception {
		double value = 8000.00d;
		Mockito.when(ebrokerService.checkFundForTrader(1L)).thenReturn(value);
		mockMvc.perform(MockMvcRequestBuilders.get("/user/1/checkFund"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(value)));
	}

	@Test
	void shouldTestCheckFundForTrader_failureCase() throws Exception {
		double value = 8000.00d;
		Mockito.when(ebrokerService.checkFundForTrader(1L)).thenReturn(value);
		mockMvc.perform(MockMvcRequestBuilders.get("/user/3/checkFund"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(0.0d)));
	}
	
	@Test
	void shouldTestUpdateFund() throws Exception { 
		double amount = 1000.00d;
		Mockito.when(ebrokerService.updateFundForTrader(1L,1000.00d)).thenReturn(1000.0d);
		mockMvc.perform(MockMvcRequestBuilders.post("/user/1/updateFund?amount="+amount))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(amount)));
	}

	@Test
	void shouldTestBuyEquity() throws Exception {
		int id = 1;
		int qty = 2;
		Mockito.when(ebrokerService.buyEquity(1L, 1, 2)).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.post("/user/1/buyEquity?equityId="+id +"&quantity="+qty))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(true)));
	}

	@Test
	void shouldTestSellEquity() throws Exception { 
		int id = 1;
		int qty = 2;
		Mockito.when(ebrokerService.sellEquity(1L, 1, 2)).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.post("/user/1/sellEquity?equityId="+id +"&quantity="+qty))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(true)));
	}

}
