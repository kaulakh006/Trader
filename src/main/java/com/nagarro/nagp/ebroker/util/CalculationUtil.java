package com.nagarro.nagp.ebroker.util;

public class CalculationUtil {
	private CalculationUtil() {

	}

	public static double balanceAfterUpdatingFunds(double currentAmount, double amount) {
		return currentAmount - amount;
	}
	
	public static double addFunds(double currentAmount, double amount) {
		return currentAmount + amount;
	}
}
