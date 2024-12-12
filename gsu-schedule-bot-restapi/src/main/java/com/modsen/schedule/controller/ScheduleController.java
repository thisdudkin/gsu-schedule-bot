package com.modsen.schedule.controller;

import com.modsen.schedule.domain.Schedule;
import com.modsen.schedule.dto.ScheduleDto;
import com.modsen.schedule.service.ScheduleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Set;

/**
 * @author Alexander Dudkin
 */
@RestController
@RequestMapping(ScheduleController.URI)
public class ScheduleController {
    public static final String URI = "/api/schedule";
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/{date}")
    public ResponseEntity<Set<ScheduleDto>> getScheduleByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(scheduleService.getScheduleForDate(date));
    }

}
