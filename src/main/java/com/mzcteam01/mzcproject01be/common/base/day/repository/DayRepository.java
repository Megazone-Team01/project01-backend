package com.mzcteam01.mzcproject01be.common.base.day.repository;

import com.mzcteam01.mzcproject01be.common.base.day.entity.Day;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayRepository extends JpaRepository<Day, Integer> {
}
