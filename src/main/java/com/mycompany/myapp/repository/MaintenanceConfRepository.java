package com.mycompany.myapp.repository;
import com.mycompany.myapp.domain.MaintenanceConf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MaintenanceConf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaintenanceConfRepository extends JpaRepository<MaintenanceConf, Long> {

}
