package co.edu.usbcali.ingesoft.repository;

import co.edu.usbcali.ingesoft.domain.Estudiante;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Estudiante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {}
