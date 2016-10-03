package com.emc.ontic.ms.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emc.ontic.ms.data.entity.SessionLocation;
import com.emc.ontic.ms.data.entity.SessionLocationPK;

@Repository
public interface SessionLocationRepository extends JpaRepository<SessionLocation, SessionLocationPK> {
	
	List<SessionLocation> findBySessionIdAndLocationId(String sessionId, String locationId);
	
}
