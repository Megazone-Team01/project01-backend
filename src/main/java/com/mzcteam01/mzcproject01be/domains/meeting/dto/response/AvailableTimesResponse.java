package com.mzcteam01.mzcproject01be.domains.meeting.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class AvailableTimesResponse {


    private int teacherId;
    private LocalDate date;
    private List<TimeSlot> timeSlots;

    @Getter
    @Builder
    public static class TimeSlot {
        private String time;
        private boolean available;
        private String status;
    }

}
