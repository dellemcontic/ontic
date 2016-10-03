package com.emc.ontic.ms.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emc.ontic.ms.data.entity.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, String> {
	
	public Session findById(String sessionID);
	
}
