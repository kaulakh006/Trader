package com.nagarro.nagp.ebroker.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nagarro.nagp.ebroker.model.Fund;

@Repository
public interface FundRepository extends JpaRepository<Fund, Long>{
	
	@Query("SELECT u from  Fund u join u.owner uu  where uu.id = :id")
	List<Fund> getFundsForTrader(@Param("id")  long l);
	
}
