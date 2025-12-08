package com.mzcteam01.mzcproject01be.domains.attendence.repository;

import com.mzcteam01.mzcproject01be.domains.attendence.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
}
