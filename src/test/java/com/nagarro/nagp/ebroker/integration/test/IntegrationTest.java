package com.nagarro.nagp.ebroker.integration.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.nagarro.nagp.ebroker.controller.EbrokerController;
import com.nagarro.nagp.ebroker.dao.EquityRepository;
import com.nagarro.nagp.ebroker.dao.FundRepository;
import com.nagarro.nagp.ebroker.dao.UserRepository;
import com.nagarro.nagp.ebroker.dao.UserStockRepository;
import com.nagarro.nagp.ebroker.model.Equity;
import com.nagarro.nagp.ebroker.model.Fund;
import com.nagarro.nagp.ebroker.model.User;
import com.nagarro.nagp.ebroker.model.UserStock;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	FundRepository fundRepository;

	@MockBean
	EquityRepository equityRepository;

	@MockBean
	UserStockRepository userStockRepository;

	@MockBean
	UserRepository userRepository;

	@Autowired
	EbrokerController ebrokerController;

	@Test
	void shouldTestCheckFundForTrader_successCase() throws Exception {
		double value = 2000.00d;
		List<Fund> funds = getFunds();
		Mockito.when(fundRepository.getFundsForTrader(1L)).thenReturn(funds);
		mockMvc.perform(MockMvcRequestBuilders.get("/user/1/checkFund"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(value)));
	}

	@Test
	void shouldTestCheckFundForTrader_failureCase() throws Exception {
		double value = 8000.00d;
		Mockito.when(fundRepository.getFundsForTrader(1L)).thenReturn(getFunds());
		mockMvc.perform(MockMvcRequestBuilders.get("/user/3/checkFund"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(0.0d)));
	}

	@Test
	void shouldTestUpdateFund_existingFund() throws Exception {
		double amount = 1000.00d;
		List<Fund> funds = getFunds();
		Mockito.when(fundRepository.getFundsForTrader(1L)).thenReturn(funds);
		Mockito.when(fundRepository.saveAndFlush(funds.get(0))).thenReturn(funds.get(0));
		mockMvc.perform(MockMvcRequestBuilders.post("/user/1/updateFund?amount=" + amount))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(3000.00d)));
	}

	@Test
	void shouldTestUpdateFund_nonExistentFund() throws Exception {
		double amount = 1000.00d;
		List<Fund> funds = getFunds();
		Mockito.when(fundRepository.getFundsForTrader(1L)).thenReturn(new ArrayList<>());
		Mockito.when(fundRepository.saveAndFlush(funds.get(0))).thenReturn(funds.get(0));
		mockMvc.perform(MockMvcRequestBuilders.post("/user/1/updateFund?amount=" + amount))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(1000.00d)));
	}

	
	@Test
	void shouldTestBuyEquity_availableFunds() throws Exception {
		int id = 1;
		int qty = 2;
		Equity e = getEquity();
		List<Fund> funds = getFunds();
		List<UserStock> us = new ArrayList<>();
		us.add(getUserStock());
		UserStock userStock = getUserStock();
		Mockito.when(equityRepository.getEquityById(1)).thenReturn(e);
		Mockito.when(userStockRepository.getExistingUserStockForEquity(1, 1L)).thenReturn(us);
		Mockito.when(userStockRepository.saveAndFlush(userStock)).thenReturn(userStock);
		Mockito.when(equityRepository.saveAndFlush(e)).thenReturn(e);
		Mockito.when(fundRepository.getFundsForTrader(1L)).thenReturn(funds);
		mockMvc.perform(MockMvcRequestBuilders.post("/user/1/buyEquity?equityId=" + id + "&quantity=" + qty))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(true)));
	}
	
	@Test
	void shouldTestBuyEquity_moreThanAvailableStock() throws Exception {
		int id = 1;
		int qty = 30;
		Equity e = getEquity();
		List<Fund> funds = getFunds();
		List<UserStock> us = new ArrayList<>();
		us.add(getUserStock());
		UserStock userStock = getUserStock();
		Mockito.when(equityRepository.getEquityById(1)).thenReturn(e);
		Mockito.when(userStockRepository.getExistingUserStockForEquity(1, 1L)).thenReturn(us);
		Mockito.when(userStockRepository.saveAndFlush(userStock)).thenReturn(userStock);
		Mockito.when(equityRepository.saveAndFlush(e)).thenReturn(e);
		Mockito.when(fundRepository.getFundsForTrader(1L)).thenReturn(funds);
		mockMvc.perform(MockMvcRequestBuilders.post("/user/1/buyEquity?equityId=" + id + "&quantity=" + qty))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(false)));
	}

	@Test
	void shouldTestBuyEquity_nonAvailableFunds() throws Exception {
		int id = 1;
		int qty = 2;
		Equity e = getEquity();
		List<Fund> funds = getFunds();
		List<UserStock> us = new ArrayList<>();
		us.add(getUserStock());
		UserStock userStock = getUserStock();
		Mockito.when(equityRepository.getEquityById(1)).thenReturn(e);
		Mockito.when(userStockRepository.getExistingUserStockForEquity(1, 1L)).thenReturn(us);
		Mockito.when(userStockRepository.saveAndFlush(userStock)).thenReturn(userStock);
		Mockito.when(equityRepository.saveAndFlush(e)).thenReturn(e);
		Mockito.when(fundRepository.getFundsForTrader(1L)).thenReturn(new ArrayList<>());
		mockMvc.perform(MockMvcRequestBuilders.post("/user/1/buyEquity?equityId=" + id + "&quantity=" + qty))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(false)));
	}
	
	@Test
	void shouldTestSellEquity_enoughQty() throws Exception {
		int id = 1;
		int qty = 1;
		Equity e = getEquity();
		List<Fund> funds = getFunds();
		List<UserStock> us = new ArrayList<>();
		us.add(getUserStock());
		UserStock userStock = getUserStock();
		Mockito.when(userStockRepository.getExistingUserStockForEquity(1, 1L)).thenReturn(us);
		Mockito.when(userStockRepository.saveAndFlush(userStock)).thenReturn(userStock);
		Mockito.when(equityRepository.saveAndFlush(e)).thenReturn(e);
		Mockito.when(fundRepository.getFundsForTrader(1L)).thenReturn(funds);
		Mockito.when(fundRepository.saveAndFlush(funds.get(0))).thenReturn(funds.get(0));
		mockMvc.perform(MockMvcRequestBuilders.post("/user/1/sellEquity?equityId=" + id + "&quantity=" + qty))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(true)));
	}
	
	@Test
	void shouldTestSellEquity_lessQty() throws Exception {
		int id = 1;
		int qty = 2;
		Equity e = getEquity();
		List<Fund> funds = getFunds();
		List<UserStock> us = new ArrayList<>();
		us.add(getUserStock());
		UserStock userStock = getUserStock();
		Mockito.when(userStockRepository.getExistingUserStockForEquity(1, 1L)).thenReturn(us);
		Mockito.when(userStockRepository.saveAndFlush(userStock)).thenReturn(userStock);
		Mockito.when(equityRepository.saveAndFlush(e)).thenReturn(e);
		Mockito.when(fundRepository.getFundsForTrader(1L)).thenReturn(funds);
		Mockito.when(fundRepository.saveAndFlush(funds.get(0))).thenReturn(funds.get(0));
		mockMvc.perform(MockMvcRequestBuilders.post("/user/1/sellEquity?equityId=" + id + "&quantity=" + qty))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(false)));
	}

	@Test
	void shouldTestSellEquity_notEnoughFunds() throws Exception {
		int id = 1;
		int qty = 1;
		Equity e = getEquity();
		List<Fund> funds = getFunds();
		List<UserStock> us = new ArrayList<>();
		us.add(getUserStock());
		UserStock userStock = getUserStock();
		Mockito.when(userStockRepository.getExistingUserStockForEquity(1, 1L)).thenReturn(new ArrayList<>());
		Mockito.when(userStockRepository.saveAndFlush(userStock)).thenReturn(userStock);
		Mockito.when(equityRepository.saveAndFlush(e)).thenReturn(e);
		Mockito.when(fundRepository.getFundsForTrader(1L)).thenReturn(new ArrayList<>());
		Mockito.when(fundRepository.saveAndFlush(funds.get(0))).thenReturn(funds.get(0));
		mockMvc.perform(MockMvcRequestBuilders.post("/user/1/sellEquity?equityId=" + id + "&quantity=" + qty))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(false)));
	}
	private List<Fund> getFunds() {
		List<Fund> funds = new ArrayList<>();
		Fund f = new Fund();
		f.setAmount(2000);
		f.setId(1);
		User u = new User();
		u.setId(1L);
		u.setEmail("aa@a.com");
		f.setOwner(u);
		funds.add(f);
		return funds;
	}

	private UserStock getUserStock() {
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
		return us;
	}

	private Equity getEquity() {
		Equity equity = new Equity();
		equity.setId(1);
		equity.setStockAmount(100);
		equity.setStockQuantity(10);
		return equity;
	}

}
