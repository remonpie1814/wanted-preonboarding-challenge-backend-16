package com.wanted.preonboarding.ticket.application.dto.response;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class PerformanceDetailResponse {
    private String performanceName;
    private int round;
    private Date startDate;
    private String isReserve;


    public static PerformanceDetailResponse of(Performance performance){
        return PerformanceDetailResponse
                .builder()
                .performanceName(performance.getName())
                .round(performance.getRound())
                .startDate(performance.getStart_date())
                .isReserve(performance.getIsReserve())
                .build();
    }

}
