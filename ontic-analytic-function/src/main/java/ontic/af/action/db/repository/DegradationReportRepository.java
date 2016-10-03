package ontic.af.action.db.repository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ontic.af.action.db.entity.DegradationReport;


@Repository
public interface DegradationReportRepository extends JpaRepository<DegradationReport,String> {
	
			
}
