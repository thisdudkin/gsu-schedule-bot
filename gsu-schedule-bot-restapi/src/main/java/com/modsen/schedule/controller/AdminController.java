package com.modsen.schedule.controller;

import com.modsen.schedule.domain.Course;
import com.modsen.schedule.domain.Group;
import com.modsen.schedule.domain.Schedule;
import com.modsen.schedule.domain.Specialty;
import com.modsen.schedule.service.dao.CourseDao;
import com.modsen.schedule.service.dao.GroupDao;
import com.modsen.schedule.service.dao.ScheduleDao;
import com.modsen.schedule.service.dao.SpecialtyDao;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Set;

/**
 * @author Alexander Dudkin
 */
@RestController
@RequestMapping(AdminController.URI)
public class AdminController {
    public static final String URI = "/demo";

    private final CourseDao courseDao;
    private final GroupDao groupDao;
    private final SpecialtyDao specialtyDao;
    private final ScheduleDao scheduleDao;

    public AdminController(CourseDao courseDao, GroupDao groupDao, SpecialtyDao specialtyDao, ScheduleDao scheduleDao) {
        this.courseDao = courseDao;
        this.groupDao = groupDao;
        this.specialtyDao = specialtyDao;
        this.scheduleDao = scheduleDao;
    }

    @PutMapping("/course")
    public void saveCourse(@RequestBody Course course) throws SQLException {
        courseDao.saveCourses(Collections.singleton(course));
    }

    @PutMapping("/specialty")
    public void saveSpecialty(@RequestBody Specialty specialty) throws SQLException {
        specialtyDao.saveSpecialties(Collections.singleton(specialty));
    }

    @PutMapping("/group")
    public void saveGroup(@RequestBody Group group) throws SQLException {
        groupDao.saveGroups(Collections.singleton(group));
    }

    @PutMapping("/schedule")
    public void saveSchedule(@RequestBody Set<Schedule> schedules) throws SQLException {
        scheduleDao.saveSchedules(schedules);
    }

}
