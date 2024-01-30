package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("performance")
@RequiredArgsConstructor
public class PerformanceController {
    private final TicketSeller ticketSeller;

    // 공연 목록
    // 예약가능 여부로 필터링 가능.
    @GetMapping("/all")
    public ResponseEntity<ResponseHandler<List<PerformanceInfo>>> getAllPerformanceInfoList(@RequestParam(value = "isReserve", required = false) String isReserve) {
        return ResponseEntity
                .ok()
                .body(ResponseHandler.<List<PerformanceInfo>>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data(ticketSeller.getAllPerformanceInfoList(isReserve))
                        .build()
                );
    }

    // 공연 이름으로 공연 찾기
    @GetMapping("/")
    public ResponseEntity<ResponseHandler<Performance>> getPerformanceInfo(@RequestParam String name){
        return ResponseEntity
                .ok()
                .body(ResponseHandler.<Performance>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data(ticketSeller.getPerformanceInfoDetail(name))
                        .build()
                );
    }

}
