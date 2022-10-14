package co.usbcali.edu.repository.rowmapper;

import co.usbcali.edu.domain.Profesor;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Profesor}, with proper type conversions.
 */
@Service
public class ProfesorRowMapper implements BiFunction<Row, String, Profesor> {

    private final ColumnConverter converter;

    public ProfesorRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Profesor} stored in the database.
     */
    @Override
    public Profesor apply(Row row, String prefix) {
        Profesor entity = new Profesor();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setNombre(converter.fromRow(row, prefix + "_nombre", String.class));
        entity.setApellido(converter.fromRow(row, prefix + "_apellido", String.class));
        entity.setCodigo(converter.fromRow(row, prefix + "_codigo", String.class));
        entity.setCorreo(converter.fromRow(row, prefix + "_correo", String.class));
        return entity;
    }
}
