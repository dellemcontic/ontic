package com.emc.ontic.ms.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emc.ontic.ms.data.entity.Kpi;

@Repository
public interface KpiRepository extends JpaRepository<Kpi, String> {
	
	public Kpi findByIdAndServiceId(String id, String service);
	
	public Kpi findById(String kpi);
	
	public List<Kpi> findByServiceId(String service);
	
}
