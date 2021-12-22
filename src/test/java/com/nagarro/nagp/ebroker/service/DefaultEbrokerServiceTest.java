package com.nagarro.nagp.ebroker.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.util.CollectionUtils;

import com.nagarro.nagp.ebroker.dao.EquityRepository;
import com.nagarro.nagp.ebroker.dao.FundRepository;
import com.nagarro.nagp.ebroker.dao.UserRepository;
import com.nagarro.nagp.ebroker.dao.UserStockRepository;
import com.nagarro.nagp.ebroker.model.Equity;
import com.nagarro.nagp.ebroker.model.Fund;
import com.nagarro.nagp.ebroker.model.User;
import com.nagarro.nagp.ebroker.model.UserStock;
import com.nagarro.nagp.ebroker.service.impl.DefaultEbrokerService;
import com.nagarro.nagp.ebroker.util.CalculationUtil;
import com.nagarro.nagp.ebroker.util.DateUtil;

public class DefaultEbrokerServiceTest {

	@Mock
	FundRepository fundRepository;

	@Mock
	EquityRepository equityRepository;

	@Mock
	UserStockRepository userStockRepository;

	@Mock
	UserRepository userRepository;

	@InjectMocks
	DefaultEbrokerService defaultEbrokerService;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void shouldTestCheckFundForTrader_successCase() {
		List<Fund> funds = getFunds();
		Mockito.when(fundRepository.getFundsForTrader(1L)).thenReturn(funds);
		final double expectedFund = defaultEbrokerService.checkFundForTrader(1L);
		double actual = 1200;
		Assertions.assertEquals(expectedFund, actual);
	}

	@Test
	void shouldTestCheckFundForTrader_failureCase() {
		Mockito.when(fundRepository.getFundsForTrader(1L)).thenReturn(new ArrayList<>());
		final double expectedFund = defaultEbrokerService.checkFundForTrader(1L);
		double actual = 0.0d;
		Assertions.assertEquals(expectedFund, actual);
	}

	@Test
	void ShouldTestUpdateFundForTrader_fundExistCase() {
		List<Fund> funds = getFunds();
		double amount = 100.00d;
		Mockito.when(fundRepository.getFundsForTrader(1L)).thenReturn(funds);
		funds.get(0).setAmount(funds.get(0).getAmount() + amount);
		Mockito.when(fundRepository.saveAndFlush(funds.get(0))).thenReturn(funds.get(0));
		double expected = defaultEbrokerService.updateFundForTrader(1L, amount);
		Assertions.assertEquals(expected, funds.get(0).getAmount());
	}

	@Test
	void ShouldTestUpdateFundForTrader_fundNotExistCase() {
		List<Fund> funds = getFunds();
		double amount = 100.00d;
		Mockito.when(fundRepository.getFundsForTrader(1L)).thenReturn(new ArrayList<>());
		funds.get(0).setAmount(funds.get(0).getAmount() + amount);
		Mockito.when(fundRepository.saveAndFlush(funds.get(0))).thenReturn(funds.get(0));
		double expected = defaultEbrokerService.updateFundForTrader(1L, amount);
		Assertions.assertEquals(expected, amount);
	}

	@Test
	void ShouldTestBuyEquity_enoughFundsExistsCase() {
		Equity e = getEquity();
		List<UserStock> us = new ArrayList<>();
		us.add(getUserStock());
		UserStock userStock = getUserStock();
		List<Fund> funds = getFunds();
		Mockito.when(equityRepository.getEquityById(1)).thenReturn(e);
		Mockito.when(userStockRepository.getExistingUserStockForEquity(1, 1L)).thenReturn(us);
		Mockito.when(userStockRepository.saveAndFlush(userStock)).thenReturn(userStock);
		Mockito.when(equityRepository.saveAndFlush(e)).thenReturn(e);
		Mockito.when(fundRepository.getFundsForTrader(1L)).thenReturn(funds);
		boolean expected = defaultEbrokerService.buyEquity(1L, 1, 1);
		Assertions.assertEquals(expected, true);
	}

	@Test
	void ShouldTestBuyEquity_noEnoughFundsCase() {
		Equity e = getEquity();
		List<UserStock> us = new ArrayList<>();
		us.add(getUserStock());
		UserStock userStock = getUserStock();
		Mockito.when(equityRepository.getEquityById(1)).thenReturn(e);
		Mockito.when(userStockRepository.getExistingUserStockForEquity(1, 1L)).thenReturn(us);
		Mockito.when(userStockRepository.saveAndFlush(userStock)).thenReturn(userStock);
		Mockito.when(equityRepository.saveAndFlush(e)).thenReturn(e);
		Mockito.when(fundRepository.getFundsForTrader(1L)).thenReturn(new ArrayList<>());
		boolean expected = defaultEbrokerService.buyEquity(1L, 1, 1);
		Assertions.assertEquals(expected, false);
	}

	@Test
	void ShouldTestSellEquity_userHasGivenStocks() {
		Equity e = getEquity();
		List<Fund> funds = getFunds();
		List<UserStock> us = new ArrayList<>();
		us.add(getUserStock());
		UserStock userStock = getUserStock();
		Mockito.when(userStockRepository.getExistingUserStockForEquity(1, 1L)).thenReturn(us);
		Mockito.when(userStockRepository.saveAndFlush(userStock)).thenReturn(userStock);
		Mockito.when(equityRepository.saveAndFlush(e)).thenReturn(e);
		Mockito.when(fundRepository.getFundsForTrader(1L)).thenReturn(new ArrayList<>());
		Mockito.when(fundRepository.saveAndFlush(funds.get(0))).thenReturn(funds.get(0));
		boolean expected = defaultEbrokerService.sellEquity(1L, 1, 1);
		Assertions.assertEquals(expected, true);
	}

	@Test
	void ShouldTestSellEquity_notEnoughStockFunds() {
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
		boolean expected = defaultEbrokerService.sellEquity(1L, 1, 1);
		Assertions.assertEquals(expected, false);
	}

	@Test
	void ShouldTestSellEquity_sellingMoreStockThanAvailable() {
		Equity e = getEquity();
		List<Fund> funds = getFunds();
		List<UserStock> us = new ArrayList<>();
		us.add(getUserStock());
		UserStock userStock = getUserStock();
		Mockito.when(userStockRepository.getExistingUserStockForEquity(1, 1L)).thenReturn(us);
		Mockito.when(userStockRepository.saveAndFlush(userStock)).thenReturn(userStock);
		Mockito.when(equityRepository.saveAndFlush(e)).thenReturn(e);
		Mockito.when(fundRepository.getFundsForTrader(1L)).thenReturn(new ArrayList<>());
		Mockito.when(fundRepository.saveAndFlush(funds.get(0))).thenReturn(funds.get(0));
		// selling more stock quantity
		boolean expected = defaultEbrokerService.sellEquity(1L, 1, 5);
		Assertions.assertEquals(expected, false);
	}

	private List<Fund> getFunds() {
		List<Fund> funds = new ArrayList<>();
		Fund f = new Fund();
		f.setAmount(1200);
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
		equity.setStockQuantity(1);
		return equity;
	}

}
