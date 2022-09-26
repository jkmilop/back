package co.edu.usbcali.ingesoft.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.usbcali.ingesoft.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstudianteCursoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstudianteCurso.class);
        EstudianteCurso estudianteCurso1 = new EstudianteCurso();
        estudianteCurso1.setId(1L);
        EstudianteCurso estudianteCurso2 = new EstudianteCurso();
        estudianteCurso2.setId(estudianteCurso1.getId());
        assertThat(estudianteCurso1).isEqualTo(estudianteCurso2);
        estudianteCurso2.setId(2L);
        assertThat(estudianteCurso1).isNotEqualTo(estudianteCurso2);
        estudianteCurso1.setId(null);
        assertThat(estudianteCurso1).isNotEqualTo(estudianteCurso2);
    }
}
