package com.mzcteam01.mzcproject01be.domains.meeting.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RejectMeetingRequest {
    private String reason;
}
