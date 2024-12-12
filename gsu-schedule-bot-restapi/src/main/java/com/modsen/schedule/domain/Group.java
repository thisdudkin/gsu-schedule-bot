package com.modsen.schedule.domain;

import java.util.UUID;

/**
 * @author Alexander Dudkin
 */
public record Group(UUID id, Course course, Specialty specialty, int groupNumber) {}
