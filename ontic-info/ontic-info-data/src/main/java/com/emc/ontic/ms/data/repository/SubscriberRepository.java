package com.emc.ontic.ms.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emc.ontic.ms.data.entity.Subscriber;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Integer> {
	
	public Subscriber findByUrl(String url);
	
	public Subscriber findById(Integer subscriberId);
	
}
