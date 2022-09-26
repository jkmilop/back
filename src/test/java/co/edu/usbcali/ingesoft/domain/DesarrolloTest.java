package co.edu.usbcali.ingesoft.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.usbcali.ingesoft.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DesarrolloTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Desarrollo.class);
        Desarrollo desarrollo1 = new Desarrollo();
        desarrollo1.setId(1L);
        Desarrollo desarrollo2 = new Desarrollo();
        desarrollo2.setId(desarrollo1.getId());
        assertThat(desarrollo1).isEqualTo(desarrollo2);
        desarrollo2.setId(2L);
        assertThat(desarrollo1).isNotEqualTo(desarrollo2);
        desarrollo1.setId(null);
        assertThat(desarrollo1).isNotEqualTo(desarrollo2);
    }
}
