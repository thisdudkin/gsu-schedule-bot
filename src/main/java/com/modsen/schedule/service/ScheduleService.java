package com.modsen.schedule.service;

import com.modsen.schedule.domain.Schedule;
import com.modsen.schedule.service.dao.ScheduleDao;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Alexander Dudkin
 */
@Service
public class ScheduleService {

    private final ScheduleDao scheduleDao;

    public ScheduleService(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    public Set<Schedule> getScheduleForDate(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        boolean isEvenWeek = date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) % 2 == 0;
        Set<Schedule> schedules  = scheduleDao.getScheduleForDate(dayOfWeek, isEvenWeek);

        List<Schedule> sortedSchedules = schedules.stream()
                .sorted(Comparator.comparing(Schedule::time))
                .toList();

        return new LinkedHashSet<>(sortedSchedules);
    }

}
