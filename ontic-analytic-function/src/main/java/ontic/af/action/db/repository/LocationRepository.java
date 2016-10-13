package ontic.af.action.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ontic.af.action.db.entity.Location;


@Repository
public interface LocationRepository extends JpaRepository<Location,Integer> {
	
			
}
