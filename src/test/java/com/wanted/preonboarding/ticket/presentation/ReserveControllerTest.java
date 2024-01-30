package com.wanted.preonboarding.ticket.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.dto.request.ReservationCancelRequest;
import com.wanted.preonboarding.ticket.application.dto.request.ReserveRequest;
import com.wanted.preonboarding.ticket.application.dto.response.ReservationCancelResponse;
import com.wanted.preonboarding.ticket.application.dto.response.ReserveResponse;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class ReserveControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnReservationOK() throws Exception {

        PerformanceSeatInfo seat = PerformanceSeatInfo
                .builder()
                .line('A')
                .seat(1)
                .round(1)
                .performanceId(UUID.fromString("5e3fff8a-3449-4230-86e8-aeb284a1955a"))
                .build();
        System.out.println(seat.getPerformanceId() + "의 " + seat.getLine() + seat.getSeat() + "에 예약 시도");

        ReserveRequest request = ReserveRequest
                .builder()
                .name("예지")
                .performanceId(seat.getPerformanceId().toString())
                .amount(1000000000)
                .round(seat.getRound())
                .line(seat.getLine())
                .seat(seat.getSeat())
                .phoneNumber("010-1111-2222")
                .build();

        ResponseHandler<ReserveResponse> response = ResponseHandler.<ReserveResponse>builder()
                .statusCode(HttpStatus.OK)
                .data(
                        ReserveResponse
                                .builder()
                                .round(seat.getRound())
                                .performanceName("맘마미아")
                                .line(seat.getLine())
                                .seat(seat.getSeat())
                                .reservationName("예지")
                                .phoneNumber("010-1111-2222")
                                .build()
                )
                .message("Success")
                .build();


        mockMvc.perform(post("/reserve/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
    }

    @Test
    public void shouldReturnReservationCancelled() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ReservationCancelRequest request = ReservationCancelRequest
                .builder()
                .performanceId("5e3fff8a-3449-4230-86e8-aeb284a1955a")
                .line('A')
                .round(1)
                .seat(1)
                .build();

        ResponseHandler<ReservationCancelResponse> response = ResponseHandler.<ReservationCancelResponse>builder()
                .statusCode(HttpStatus.OK)
                .data(
                        ReservationCancelResponse.builder()
                                .performanceId(UUID.fromString("5e3fff8a-3449-4230-86e8-aeb284a1955a"))
                                .performanceName("맘마미아")
                                .line('A')
                                .round(1)
                                .seat(1)
                                .startDate("2099-02-23 13:30:00.0")
                                .build()
                )
                .build();


        mockMvc.perform(post("/reserve/cancel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
    }


}
