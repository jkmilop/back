package co.edu.usbcali.ingesoft.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ActividadMapperTest {

    private ActividadMapper actividadMapper;

    @BeforeEach
    public void setUp() {
        actividadMapper = new ActividadMapperImpl();
    }
}
