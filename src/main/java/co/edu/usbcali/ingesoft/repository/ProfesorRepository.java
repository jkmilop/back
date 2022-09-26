package co.edu.usbcali.ingesoft.repository;

import co.edu.usbcali.ingesoft.domain.Profesor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Profesor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {}
