package com.emc.ontic.ms.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emc.ontic.ms.data.entity.SubscriptionKpi;

@Repository
public interface SubscriptionKpiRepository extends JpaRepository<SubscriptionKpi, String> {
	
	public List<SubscriptionKpi> findBySubscriptionId(String subscriptionId);
	
	public List<SubscriptionKpi> findByKpiId(String kpiId);
	
}
