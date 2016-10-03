package ontic.af.action.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ontic.af.action.db.entity.AffectedLocation;
import ontic.af.action.db.entity.AffectedLocation_PK;



@Repository
public interface AffectedLocationRepository extends JpaRepository<AffectedLocation,AffectedLocation_PK> {
	
			
}
