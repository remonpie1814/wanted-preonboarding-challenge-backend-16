package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("query")
@RequiredArgsConstructor
public class QueryController {
    private final TicketSeller ticketSeller;

    @GetMapping("/all/performance")
    public ResponseEntity<ResponseHandler<List<PerformanceInfo>>> getAllPerformanceInfoList(@RequestParam(value = "isReserve", required = false) String isReserve) {
        System.out.println(isReserve+"--------");
        if(isReserve==null){
            isReserve="enable";
        }
        System.out.println("getAllPerformanceInfoList");
        return ResponseEntity
            .ok()
            .body(ResponseHandler.<List<PerformanceInfo>>builder()
                .message("Success")
                .statusCode(HttpStatus.OK)
                .data(ticketSeller.getAllPerformanceInfoList(isReserve))
                .build()
            );
    }


    // note: 예약 목록을 그냥 모두 가져오는 api. 테스트용.
    @GetMapping("/all/reservation")
    public ResponseEntity<ResponseHandler<List<Reservation>>> getAllReservationList() {
        System.out.println("getAllReservation");

        return ResponseEntity
                .ok()
                .body(ResponseHandler.<List<Reservation>>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data(ticketSeller.getAllReservationList())
                        .build()
                );
    }

    @GetMapping("/performance")
    public ResponseEntity<ResponseHandler<PerformanceInfo>> getPerformanceInfo(@RequestParam String name){
        return ResponseEntity
                .ok()
                .body(ResponseHandler.<PerformanceInfo>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data(ticketSeller.getPerformanceInfoDetail(name))
                        .build()
                );
    }


}
