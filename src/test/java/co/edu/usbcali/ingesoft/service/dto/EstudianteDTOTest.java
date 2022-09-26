package co.edu.usbcali.ingesoft.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.usbcali.ingesoft.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstudianteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstudianteDTO.class);
        EstudianteDTO estudianteDTO1 = new EstudianteDTO();
        estudianteDTO1.setId(1L);
        EstudianteDTO estudianteDTO2 = new EstudianteDTO();
        assertThat(estudianteDTO1).isNotEqualTo(estudianteDTO2);
        estudianteDTO2.setId(estudianteDTO1.getId());
        assertThat(estudianteDTO1).isEqualTo(estudianteDTO2);
        estudianteDTO2.setId(2L);
        assertThat(estudianteDTO1).isNotEqualTo(estudianteDTO2);
        estudianteDTO1.setId(null);
        assertThat(estudianteDTO1).isNotEqualTo(estudianteDTO2);
    }
}
