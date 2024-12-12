package com.modsen.schedule.domain;

import com.modsen.schedule.enums.WeekType;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

/**
 * @author Alexander Dudkin
 */
public record Schedule(UUID id, Group group, DayOfWeek dayOfWeek, LocalTime time, String subject, String lecturer, String room, WeekType weekType) {}
