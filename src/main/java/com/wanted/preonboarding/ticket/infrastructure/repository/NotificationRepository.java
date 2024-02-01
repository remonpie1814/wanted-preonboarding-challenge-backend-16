package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByNameAndPhoneNumber(String name, String phoneNumber);
    Optional<Notification> findOneByNameAndPhoneNumberAndPerformanceId(String name, String phoneNumber, UUID performanceId);
}
