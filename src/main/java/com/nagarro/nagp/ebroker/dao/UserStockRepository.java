package com.nagarro.nagp.ebroker.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nagarro.nagp.ebroker.model.Fund;
import com.nagarro.nagp.ebroker.model.UserStock;

@Repository
public interface UserStockRepository extends JpaRepository<UserStock, Long>{

	@Query("SELECT u from  UserStock u join u.equity uu join u.user rr where uu.id = :id and rr.id= :userid")
	List<UserStock> getExistingUserStockForEquity(@Param("id")  int l,@Param("userid")  long u);
}
