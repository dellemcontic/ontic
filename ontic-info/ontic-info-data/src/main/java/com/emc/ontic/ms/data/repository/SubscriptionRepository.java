package com.emc.ontic.ms.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emc.ontic.ms.data.entity.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, String> {
	
	Subscription findById(String subscriptionId);
	
}
