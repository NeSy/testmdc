package com.mycompany.myapp.repository;
import com.mycompany.myapp.domain.Eqpt;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Eqpt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EqptRepository extends JpaRepository<Eqpt, Long> {

}
