package com.modsen.schedule.service.dao;

import com.modsen.schedule.domain.Specialty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Collection;
import java.util.UUID;

/**
 * @author Alexander Dudkin
 */
@Repository
public class SpecialtyDao {

    private final JdbcTemplate jdbcTemplate;

    public SpecialtyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveSpecialties(Collection<Specialty> specialties) throws SQLException {
        jdbcTemplate.execute("insert into specialties(id, name, short_name) values (?, ?, ?)",
                (PreparedStatementCallback<?>) ps -> {
                    for (Specialty specialty : specialties) {
                        ps.setObject(1, UUID.randomUUID());
                        ps.setString(2, specialty.name());
                        ps.setString(3, specialty.shortName());
                        ps.execute();
                    }
                    return null;
                });
    }

}
