package com.nagarro.nagp.ebroker.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nagarro.nagp.ebroker.model.Equity;

@Repository
public interface EquityRepository extends JpaRepository<Equity, Integer> {

	Equity getEquityById(int equityId);
	//List<Equity> getEquityForOwner(int ownerId);

}
