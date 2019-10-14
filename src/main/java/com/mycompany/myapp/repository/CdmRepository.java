package com.mycompany.myapp.repository;
import com.mycompany.myapp.domain.Cdm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Cdm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CdmRepository extends JpaRepository<Cdm, Long> {

}
