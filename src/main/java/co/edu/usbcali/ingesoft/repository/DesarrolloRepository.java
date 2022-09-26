package co.edu.usbcali.ingesoft.repository;

import co.edu.usbcali.ingesoft.domain.Desarrollo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Desarrollo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DesarrolloRepository extends JpaRepository<Desarrollo, Long> {}
