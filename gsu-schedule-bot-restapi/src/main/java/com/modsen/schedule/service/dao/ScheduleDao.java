package com.modsen.schedule.service.dao;

import com.modsen.schedule.domain.Group;
import com.modsen.schedule.domain.Schedule;
import com.modsen.schedule.dto.ScheduleDto;
import com.modsen.schedule.enums.WeekType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Alexander Dudkin
 */
@Repository
public class ScheduleDao {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Group fetchGroup() throws SQLException {
        final AtomicReference<Group> result = new AtomicReference<>();
        jdbcTemplate.execute("select g.id, g.group_number, g.course_id, g.specialty_id " +
                "from groups g " +
                "join specialties s on g.specialty_id = s.id " +
                "where s.short_name = 'ИТП'", (PreparedStatementCallback<?>) ps -> {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result.set(new Group(
                            rs.getObject("id", UUID.class),
                            null,
                            null,
                            rs.getInt("group_number")
                    ));
                }
            }
            return null;
        });
        return result.get();
    }

    private Schedule createSchedule(ResultSet resultSet) throws SQLException {
        return new Schedule(
                resultSet.getObject("id", UUID.class),
                fetchGroup(),
                DayOfWeek.valueOf(resultSet.getString("day_of_week")),
                resultSet.getObject("time", LocalTime.class),
                resultSet.getString("subject"),
                resultSet.getString("lecturer"),
                resultSet.getString("room"),
                WeekType.valueOf(resultSet.getString("week_type"))
        );
    }

    public void saveSchedules(Collection<Schedule> schedules) throws SQLException {
        jdbcTemplate.execute("insert into schedule(id, group_id, day_of_week, time, subject, lecturer, room, week_type) values (?, ?, ?, ?, ?, ?, ?, ?)",
                (PreparedStatementCallback<?>) ps -> {
                    for (Schedule schedule : schedules) {
                        ps.setObject(1, UUID.randomUUID());
                        ps.setObject(2, schedule.group().id());
                        ps.setString(3, schedule.dayOfWeek().name());
                        ps.setObject(4, schedule.time());
                        ps.setString(5, schedule.subject());
                        ps.setString(6, schedule.lecturer());
                        ps.setString(7, schedule.room());
                        ps.setString(8, schedule.weekType().name());
                        ps.execute();
                    }
                    return null;
                });
    }

    public Set<Schedule> getScheduleForDate(DayOfWeek dayOfWeek, boolean isEvenWeek) {
        final AtomicReference<Set<Schedule>> result = new AtomicReference<>();
        jdbcTemplate.execute("select id, group_id, day_of_week, time, subject, lecturer, room, week_type " +
                        "from schedule " +
                        "where day_of_week = ? and (week_type = 'BOTH' or (week_type = 'EVEN' and ?) or (week_type = 'ODD' and not ?))",
                (PreparedStatementCallback<?>) ps -> {
                    ps.setString(1, dayOfWeek.name());
                    ps.setBoolean(2, isEvenWeek);
                    ps.setBoolean(3, isEvenWeek);

                    try (ResultSet rs = ps.executeQuery()) {
                        Set<Schedule> temp = new HashSet<>();
                        while (rs.next()) {
                            temp.add(createSchedule(rs));
                        }
                        result.set(temp);
                    }
                    return null;
                });
        return result.get();
    }

    public Set<ScheduleDto> getScheduleDtoForDate(DayOfWeek dayOfWeek, boolean isEvenWeek) {
        final AtomicReference<Set<ScheduleDto>> result = new AtomicReference<>();
        jdbcTemplate.execute("select id, group_id, day_of_week, time, subject, lecturer, room, week_type " +
                        "from schedule " +
                        "where day_of_week = ? and (week_type = 'BOTH' or (week_type = 'EVEN' and ?) or (week_type = 'ODD' and not ?))",
                (PreparedStatementCallback<?>) ps -> {
                    ps.setString(1, dayOfWeek.name());
                    ps.setBoolean(2, isEvenWeek);
                    ps.setBoolean(3, isEvenWeek);

                    try (ResultSet rs = ps.executeQuery()) {
                        Set<ScheduleDto> temp = new HashSet<>();
                        while (rs.next()) {
                            temp.add(createScheduleDto(rs));
                        }
                        result.set(temp);
                    }
                    return null;
                });
        return result.get();
    }

    private ScheduleDto createScheduleDto(ResultSet resultSet) throws SQLException {
        return new ScheduleDto(
                resultSet.getObject("id", UUID.class),
                resultSet.getObject("group_id", UUID.class),
                DayOfWeek.valueOf(resultSet.getString("day_of_week")),
                resultSet.getObject("time", LocalTime.class),
                resultSet.getString("subject"),
                resultSet.getString("lecturer"),
                resultSet.getString("room"),
                WeekType.valueOf(resultSet.getString("week_type"))
        );
    }


}
