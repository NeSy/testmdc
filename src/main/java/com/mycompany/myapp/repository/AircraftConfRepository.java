package com.mycompany.myapp.repository;
import com.mycompany.myapp.domain.AircraftConf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AircraftConf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AircraftConfRepository extends JpaRepository<AircraftConf, Long> {

}
