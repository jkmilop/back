package co.edu.usbcali.ingesoft.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.usbcali.ingesoft.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DesarrolloDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DesarrolloDTO.class);
        DesarrolloDTO desarrolloDTO1 = new DesarrolloDTO();
        desarrolloDTO1.setId(1L);
        DesarrolloDTO desarrolloDTO2 = new DesarrolloDTO();
        assertThat(desarrolloDTO1).isNotEqualTo(desarrolloDTO2);
        desarrolloDTO2.setId(desarrolloDTO1.getId());
        assertThat(desarrolloDTO1).isEqualTo(desarrolloDTO2);
        desarrolloDTO2.setId(2L);
        assertThat(desarrolloDTO1).isNotEqualTo(desarrolloDTO2);
        desarrolloDTO1.setId(null);
        assertThat(desarrolloDTO1).isNotEqualTo(desarrolloDTO2);
    }
}
