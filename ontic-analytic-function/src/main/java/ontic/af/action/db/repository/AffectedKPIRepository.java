package ontic.af.action.db.repository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ontic.af.action.db.entity.AffectedKPI;
import ontic.af.action.db.entity.AffectedKPI_PK;
import ontic.af.action.db.entity.DegradationReport;


@Repository
public interface AffectedKPIRepository extends JpaRepository<AffectedKPI,AffectedKPI_PK> {
	
			
}
