package com.emc.ontic.ms.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emc.ontic.ms.data.entity.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
	
}
