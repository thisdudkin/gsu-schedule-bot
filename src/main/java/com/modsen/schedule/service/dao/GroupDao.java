package com.modsen.schedule.service.dao;

import com.modsen.schedule.domain.Group;
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
public class GroupDao {

    private final JdbcTemplate jdbcTemplate;

    public GroupDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveGroups(Collection<Group> groups) throws SQLException {
        jdbcTemplate.execute("insert into groups(id, course_id, specialty_id, group_number) values (?, ?, ?, ?)",
                (PreparedStatementCallback<?>) ps -> {
                    for (Group group : groups) {
                        ps.setObject(1, UUID.randomUUID());
                        ps.setObject(2, group.course().id());
                        ps.setObject(3, group.specialty().id());
                        ps.setInt(4, group.groupNumber());
                        ps.execute();
                    }
                    return null;
                });
    }

}
