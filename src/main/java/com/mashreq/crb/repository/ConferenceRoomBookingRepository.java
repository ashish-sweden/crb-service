package com.mashreq.crb.repository;

import com.mashreq.crb.entity.ConferenceRoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConferenceRoomBookingRepository extends JpaRepository<ConferenceRoomBooking, Long> {

    List<ConferenceRoomBooking> findByIsRoomReleasedFalseAndEndTimeBetween(LocalDateTime start, LocalDateTime end);

}
