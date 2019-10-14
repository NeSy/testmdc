package com.mycompany.myapp.repository;
import com.mycompany.myapp.domain.DicoCDM;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DicoCDM entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DicoCDMRepository extends JpaRepository<DicoCDM, Long> {

}
