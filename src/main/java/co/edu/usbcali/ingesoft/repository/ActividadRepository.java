package co.edu.usbcali.ingesoft.repository;

import co.edu.usbcali.ingesoft.domain.Actividad;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Actividad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Long> {}
