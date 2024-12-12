package com.modsen.schedule.service.dao;

import com.modsen.schedule.domain.Course;
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
public class CourseDao {

    private final JdbcTemplate jdbcTemplate;

    public CourseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveCourses(Collection<Course> courses) throws SQLException {
        jdbcTemplate.execute("insert into courses(id, number) values (?, ?)",
                (PreparedStatementCallback<?>) ps -> {
                    for (Course course : courses) {
                        ps.setObject(1, UUID.randomUUID());
                        ps.setInt(2, course.number());
                        ps.execute();
                    }
                    return null;
                });
    }

}
