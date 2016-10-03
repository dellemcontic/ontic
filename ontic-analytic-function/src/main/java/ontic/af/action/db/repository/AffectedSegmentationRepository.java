package ontic.af.action.db.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ontic.af.action.db.entity.AffectedSegmentation;
import ontic.af.action.db.entity.AffectedSegmentation_PK;



@Repository
public interface AffectedSegmentationRepository extends JpaRepository<AffectedSegmentation,AffectedSegmentation_PK> {
	
			
}
