package co.edu.usbcali.ingesoft.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.usbcali.ingesoft.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstudianteCursoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstudianteCursoDTO.class);
        EstudianteCursoDTO estudianteCursoDTO1 = new EstudianteCursoDTO();
        estudianteCursoDTO1.setId(1L);
        EstudianteCursoDTO estudianteCursoDTO2 = new EstudianteCursoDTO();
        assertThat(estudianteCursoDTO1).isNotEqualTo(estudianteCursoDTO2);
        estudianteCursoDTO2.setId(estudianteCursoDTO1.getId());
        assertThat(estudianteCursoDTO1).isEqualTo(estudianteCursoDTO2);
        estudianteCursoDTO2.setId(2L);
        assertThat(estudianteCursoDTO1).isNotEqualTo(estudianteCursoDTO2);
        estudianteCursoDTO1.setId(null);
        assertThat(estudianteCursoDTO1).isNotEqualTo(estudianteCursoDTO2);
    }
}
