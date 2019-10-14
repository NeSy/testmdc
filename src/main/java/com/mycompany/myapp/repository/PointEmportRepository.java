package com.mycompany.myapp.repository;
import com.mycompany.myapp.domain.PointEmport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PointEmport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PointEmportRepository extends JpaRepository<PointEmport, Long> {

}
