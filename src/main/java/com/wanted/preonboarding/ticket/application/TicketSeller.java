package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.core.exception.NotEnoughAmountException;
import com.wanted.preonboarding.core.exception.NotFoundException;
import com.wanted.preonboarding.ticket.application.dto.request.ReservationCancelRequest;
import com.wanted.preonboarding.ticket.application.dto.request.ReserveRequest;
import com.wanted.preonboarding.ticket.application.dto.response.ReservationCancelResponse;
import com.wanted.preonboarding.ticket.application.dto.response.ReserveResponse;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("ALL")
@Service
@Slf4j
@RequiredArgsConstructor
public class TicketSeller {
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    private final PerformanceSeatInfoRepository seatInfoRepository;

    public List<PerformanceInfo> getAllPerformanceInfoList(String isReserve) {
        return performanceRepository.findByIsReserve(isReserve)
                .stream()
                .map(PerformanceInfo::of)
                .toList();
    }

    public PerformanceInfo getPerformanceInfoDetail(String name) {
        return PerformanceInfo.of(performanceRepository.findByName(name));
    }

    @Transactional(rollbackOn = Exception.class)
    public ReserveResponse reserve(ReserveRequest request) throws NotFoundException, NotEnoughAmountException {
        // 예약정보
        Reservation reservation = request.getReservation();
        // 공연정보
        Performance performance = performanceRepository.findByIdAndIsReserve(UUID.fromString(request.getPerformanceId()), "enable")
                .orElseThrow(()->new NotFoundException("Performance"));
        // 좌석정보
        PerformanceSeatInfo seatInfo = seatInfoRepository
                .findByPerformanceIdAndRoundAndLineAndSeatAndIsReserve(
                        UUID.fromString(request.getPerformanceId()),
                        request.getRound(),
                        request.getLine(),
                        request.getSeat(),
                        "enable"
                ).orElseThrow(()-> new NotFoundException("Seat"));
        // 지불
        request.getUser().pay(performance.getPrice());

        // 좌석
        seatInfo.changeEnable();
        seatInfoRepository.save(seatInfo);

        // 예약
        reservationRepository.save(reservation);

        return ReserveResponse.of(reservation, performance);
    }

    // 모든 예약 목록
    public List<Reservation> getAllReservationList() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationByNameAndPhone(String name, String phoneNumber) {
        return reservationRepository.findByNameAndPhoneNumber(name, phoneNumber);
    }

    public String getPerformanceName(UUID performanceId) {
        return performanceRepository.findById(performanceId).get().getName();
    }

    @Transactional(rollbackOn = Exception.class)
    public ReservationCancelResponse cancelReservation(ReservationCancelRequest request) throws NotFoundException {
        Performance targetPerformance = performanceRepository.findById(UUID.fromString(request.getPerformanceId()))
                .orElseThrow(()->new NotFoundException("Performance"));
        Reservation targetReservation = reservationRepository
                .findByPerformanceIdAndRoundAndLineAndSeat(
                        UUID.fromString(request.getPerformanceId()),
                        request.getRound(),
                        request.getLine(),
                        request.getSeat()
                )
                .orElseThrow(()->new NotFoundException("Reservation"));
        reservationRepository.delete(targetReservation);

        return ReservationCancelResponse.of(targetReservation,targetPerformance);
    }
}
