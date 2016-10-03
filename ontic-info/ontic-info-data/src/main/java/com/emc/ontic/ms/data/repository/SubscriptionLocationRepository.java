package com.emc.ontic.ms.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emc.ontic.ms.data.entity.SubscriptionLocation;

@Repository
public interface SubscriptionLocationRepository extends JpaRepository<SubscriptionLocation, String> {
	
	public List<SubscriptionLocation> findBySubscriptionId(String subscriptionId);
	
	public List<SubscriptionLocation> findByLocationId(String locationId);
	
}
