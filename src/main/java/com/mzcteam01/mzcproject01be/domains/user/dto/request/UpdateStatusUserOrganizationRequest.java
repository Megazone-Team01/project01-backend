package com.mzcteam01.mzcproject01be.domains.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateStatusUserOrganizationRequest {
    private int userOrganizationId;
    private int status;
}
