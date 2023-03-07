package com.project.PAS.core.dto.pas;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class PasDTO {
    private Long accountNumber;
    private String firstName;
    private String lastname;
    private Long age;
    private Long policyNumber;
    private LocalDate effectiveDate;
    private LocalDate expirationDate;
    private String color;
    private String type;
}
