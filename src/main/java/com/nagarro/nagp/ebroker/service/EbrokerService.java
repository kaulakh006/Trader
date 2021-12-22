package com.nagarro.nagp.ebroker.service;

public interface EbrokerService {

	double updateFundForTrader(long traderId, double amount);
	double checkFundForTrader(long traderId);
	boolean buyEquity(long traderId,int equityId, int equityQuantity);
	boolean sellEquity(long traderId, int equityId, int equityQuantity);
	double addFund(long traderId, double amount);
}
