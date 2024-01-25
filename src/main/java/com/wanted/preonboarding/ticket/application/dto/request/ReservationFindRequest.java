package com.wanted.preonboarding.ticket.application.dto.request;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationFindRequest {
    private String name;
    private String phoneNumber;

}
