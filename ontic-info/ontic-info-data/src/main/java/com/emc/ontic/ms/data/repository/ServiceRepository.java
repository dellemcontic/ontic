package com.emc.ontic.ms.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emc.ontic.ms.data.entity.Location;

@Repository
public interface ServiceRepository extends JpaRepository<Location, String> {
	
	public List<Location> findAll();
	
}
