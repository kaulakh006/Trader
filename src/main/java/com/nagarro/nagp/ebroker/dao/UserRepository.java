package com.nagarro.nagp.ebroker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagarro.nagp.ebroker.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
