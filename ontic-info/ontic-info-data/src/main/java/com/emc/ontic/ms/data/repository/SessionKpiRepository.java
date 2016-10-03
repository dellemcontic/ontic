package com.emc.ontic.ms.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emc.ontic.ms.data.entity.SessionKpi;
import com.emc.ontic.ms.data.entity.SessionKpiPK;

@Repository
public interface SessionKpiRepository extends JpaRepository<SessionKpi, SessionKpiPK> {
	
	List<SessionKpi> findBySessionIdAndKpiId(String sessionId, String kpiId);
}
