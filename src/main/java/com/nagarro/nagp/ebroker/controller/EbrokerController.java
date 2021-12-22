package com.nagarro.nagp.ebroker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.nagp.ebroker.model.Equity;
import com.nagarro.nagp.ebroker.service.EbrokerService;
import com.nagarro.nagp.ebroker.util.DateUtil;

@RestController
public class EbrokerController {

	@Autowired
	EbrokerService ebrokerService;

	@GetMapping("/user/{userId}/checkFund")
	public double checkFundForTrader(@PathVariable long userId) {
		return ebrokerService.checkFundForTrader(userId);
	}

	@PostMapping("/user/{userId}/updateFund")
	public double updateFund(@PathVariable long userId, @RequestParam double amount) {
		return ebrokerService.updateFundForTrader(userId, amount);
	}

	@PostMapping("/user/{userId}/buyEquity")
	public boolean buyEquity(@PathVariable long userId, @RequestParam int equityId, @RequestParam int quantity) {
		return DateUtil.isValidPeriod() ? ebrokerService.buyEquity(userId, equityId, quantity) : Boolean.FALSE;
	}

	@PostMapping("/user/{userId}/sellEquity")
	public boolean sellEquity(@PathVariable long userId, @RequestParam int equityId, @RequestParam int quantity) {
		return ebrokerService.sellEquity(userId, equityId, quantity);
	}
}
