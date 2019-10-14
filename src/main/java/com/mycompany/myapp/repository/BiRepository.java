package com.mycompany.myapp.repository;
import com.mycompany.myapp.domain.Bi;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Bi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BiRepository extends JpaRepository<Bi, Long> {

}
