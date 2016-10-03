package com.emc.ontic.ms.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emc.ontic.ms.data.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	public List<User> findAll();
	
}
