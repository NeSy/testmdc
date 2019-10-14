package com.mycompany.myapp.repository;
import com.mycompany.myapp.domain.Td;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Td entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TdRepository extends JpaRepository<Td, Long> {

}
