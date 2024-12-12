package com.modsen.schedule.dto;

import com.modsen.schedule.enums.WeekType;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

/**
 * @author Alexander Dudkin
 */
public record ScheduleDto(UUID id, UUID groupId, DayOfWeek dayOfWeek, LocalTime time, String subject, String lecturer, String room, WeekType weekType) implements Serializable {}
