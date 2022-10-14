package co.usbcali.edu.repository.rowmapper;

import co.usbcali.edu.domain.Curso;
import co.usbcali.edu.domain.enumeration.TipoCurso;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Curso}, with proper type conversions.
 */
@Service
public class CursoRowMapper implements BiFunction<Row, String, Curso> {

    private final ColumnConverter converter;

    public CursoRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Curso} stored in the database.
     */
    @Override
    public Curso apply(Row row, String prefix) {
        Curso entity = new Curso();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setNombre(converter.fromRow(row, prefix + "_nombre", String.class));
        entity.setDescripcion(converter.fromRow(row, prefix + "_descripcion", String.class));
        entity.setEstado(converter.fromRow(row, prefix + "_estado", Boolean.class));
        entity.setTipoCurso(converter.fromRow(row, prefix + "_tipo_curso", TipoCurso.class));
        entity.setProfesorId(converter.fromRow(row, prefix + "_profesor_id", Long.class));
        return entity;
    }
}
