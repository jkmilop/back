package co.edu.usbcali.ingesoft.repository;

import co.edu.usbcali.ingesoft.domain.EstudianteCurso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EstudianteCurso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstudianteCursoRepository extends JpaRepository<EstudianteCurso, Long> {}
