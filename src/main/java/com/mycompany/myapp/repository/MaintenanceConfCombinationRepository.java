package com.mycompany.myapp.repository;
import com.mycompany.myapp.domain.MaintenanceConfCombination;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MaintenanceConfCombination entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaintenanceConfCombinationRepository extends JpaRepository<MaintenanceConfCombination, Long> {

}
