package com.piotrgrochowiecki.eriderentapigateway.remote.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import java.time.LocalDate;

@Builder
@JsonIgnoreProperties
public record UserResponseDto(Long id,
                              String uuid,
                              String firstName,
                              String lastName,
                              String email,
                              LocalDate drivingLicenseIssueDate) {

}
