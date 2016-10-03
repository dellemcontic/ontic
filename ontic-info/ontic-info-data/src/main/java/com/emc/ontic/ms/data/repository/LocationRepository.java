package com.emc.ontic.ms.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emc.ontic.ms.data.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {
	
	public Location findById(String id);
	
}
