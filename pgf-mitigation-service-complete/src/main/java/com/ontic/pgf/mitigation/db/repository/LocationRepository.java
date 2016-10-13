package com.ontic.pgf.mitigation.db.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ontic.pgf.mitigation.db.entity.Location;


@Repository
public interface LocationRepository extends JpaRepository<Location,Integer> {
	
			
}
