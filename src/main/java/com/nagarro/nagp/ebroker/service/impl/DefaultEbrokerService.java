package com.nagarro.nagp.ebroker.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.nagarro.nagp.ebroker.dao.EquityRepository;
import com.nagarro.nagp.ebroker.dao.FundRepository;
import com.nagarro.nagp.ebroker.dao.UserRepository;
import com.nagarro.nagp.ebroker.dao.UserStockRepository;
import com.nagarro.nagp.ebroker.model.Equity;
import com.nagarro.nagp.ebroker.model.Fund;
import com.nagarro.nagp.ebroker.model.User;
import com.nagarro.nagp.ebroker.model.UserStock;
import com.nagarro.nagp.ebroker.service.EbrokerService;
import com.nagarro.nagp.ebroker.util.CalculationUtil;
import com.nagarro.nagp.ebroker.util.DateUtil;

@Service
public class DefaultEbrokerService implements EbrokerService {

	@Autowired
	FundRepository fundRepository;

	@Autowired
	EquityRepository equityRepository;

	@Autowired
	UserStockRepository userStockRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public double updateFundForTrader(long traderId, double amount) {
		List<Fund> funds = fundRepository.getFundsForTrader(traderId);
		if (!CollectionUtils.isEmpty(funds)) {
			double currentAmount = funds.get(0).getAmount();
			double updateBalance = CalculationUtil.addFunds(currentAmount, amount);
			funds.get(0).setAmount(updateBalance);
			fundRepository.saveAndFlush(funds.get(0));
			return updateBalance;
		} else {
			Fund newFund = new Fund();
			newFund.setAmount(amount);
			Optional<User> user = getUser(traderId);
			if (user.isPresent())
				newFund.setOwner(user.get());
			fundRepository.saveAndFlush(newFund);
			return amount;
		}
	}

	@Override
	public double checkFundForTrader(long traderId) {
		List<Fund> funds = fundRepository.getFundsForTrader(traderId);
		double amount = 0.0d;
		for (Fund f : funds)
			amount += f.getAmount();
		return amount;
	}

	@Override
	public boolean buyEquity(long traderId, int equityId, int equityQuantity) {
		double currentFunds = checkFundForTrader(traderId);
		boolean isSuccess = false;
		Equity equity = equityRepository.getEquityById(equityId);
		// Stock should be available as requested by user and user must have funds
		if (currentFunds >= (equity.getStockAmount() * equityQuantity) && equity.getStockQuantity() >= equityQuantity) {
			List<UserStock> existingUserStocks = userStockRepository.getExistingUserStockForEquity(equityId, traderId);
			if (!CollectionUtils.isEmpty(existingUserStocks)) {
				UserStock existingUserStock = existingUserStocks.get(0);
				int newQuantity = (int) (existingUserStock.getQuantity() + equityQuantity);
				double newAmount = existingUserStock.getTotalAmount() + (equity.getStockAmount() * equityQuantity);
				existingUserStock.setQuantity(newQuantity);
				existingUserStock.setTotalAmount(newAmount);
				userStockRepository.saveAndFlush(existingUserStocks.get(0));
				updateFundBalance(traderId, equityQuantity, currentFunds, equity);
				equity.setStockQuantity(equity.getStockQuantity() - equityQuantity);
				equityRepository.saveAndFlush(equity);
				isSuccess = true;
			} else {
				UserStock userStock = new UserStock();
				userStock.setEquity(equity);
				userStock.setQuantity(equityQuantity);
				userStock.setTotalAmount(equity.getStockAmount() * equityQuantity);
				Optional<User> user = getUser(traderId);
				if (user.isPresent()) {
					userStock.setUser(user.get());
				}
				userStockRepository.saveAndFlush(userStock);
				updateFundBalance(traderId, equityQuantity, currentFunds, equity);
				equity.setStockQuantity(equity.getStockQuantity() - equityQuantity);
				equityRepository.saveAndFlush(equity);
				isSuccess = true;
			}

		}
		return isSuccess;
	}

	private void updateFundBalance(long traderId, int equityQuantity, double currentFunds, Equity equity) {
		double balance = CalculationUtil.balanceAfterUpdatingFunds(currentFunds,
				(equity.getStockAmount() * equityQuantity));
		updateBalance(traderId, balance);
	}

	private void updateBalance(long traderId, double currentFunds) {
		List<Fund> funds = fundRepository.getFundsForTrader(traderId);
		if (!CollectionUtils.isEmpty(funds)) {
			funds.get(0).setAmount(currentFunds);
			fundRepository.saveAndFlush(funds.get(0));
		} else {
			Fund newFund = new Fund();
			newFund.setAmount(currentFunds);
			Optional<User> user = getUser(traderId);
			if (user.isPresent())
				newFund.setOwner(user.get());
			fundRepository.saveAndFlush(newFund);
		}
	}

	private Optional<User> getUser(long traderId) {
		Optional<User> user = userRepository.findById(traderId);
		return user;
	}

	@Override
	public boolean sellEquity(long traderId, int equityId, int equityQuantity) {
		List<UserStock> existingUserStocks = userStockRepository.getExistingUserStockForEquity(equityId, traderId);
		if (!CollectionUtils.isEmpty(existingUserStocks) && DateUtil.isValidPeriod()) {
			// Release stock from user
			UserStock exsitingStock = existingUserStocks.get(0);
			int qty = exsitingStock.getQuantity();
			Equity equity = exsitingStock.getEquity();
			int newQty = qty - equityQuantity;
			if (newQty >=  0) {
				int newAmount = (int) (exsitingStock.getTotalAmount() - (newQty * equity.getStockAmount()));

				// Db changes
				exsitingStock.setQuantity(newQty);
				exsitingStock.setTotalAmount(newAmount);
				userStockRepository.saveAndFlush(exsitingStock);

				// IncreaseUserFund
				updateFundForTrader(traderId, newAmount);

				// Add back to equity
				equity.setStockQuantity(equity.getStockQuantity() + equityQuantity);
				equityRepository.saveAndFlush(equity);

				return true;
			}
		}
		return false;
	}

	@Override
	public double addFund(long traderId, double amount) {
		return updateFundForTrader(traderId, amount);
	}

}
