package com.mzcteam01.mzcproject01be.common.base.day.repository;

import com.mzcteam01.mzcproject01be.common.base.day.entity.Day;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DayRepository extends JpaRepository<Day, Integer> {
    Optional<Day> findByValue( int value );
    Optional<Day> findByName( String name );
}
