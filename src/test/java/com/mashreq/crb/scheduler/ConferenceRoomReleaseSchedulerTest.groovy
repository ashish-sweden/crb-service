package com.mashreq.crb.scheduler

import com.mashreq.crb.entity.ConferenceRoom
import com.mashreq.crb.entity.ConferenceRoomBooking
import com.mashreq.crb.repository.ConferenceRoomBookingRepository
import com.mashreq.crb.repository.ConferenceRoomRepository
import spock.lang.Specification
import java.time.LocalDateTime

class ConferenceRoomReleaseSchedulerTest extends Specification {

    ConferenceRoomRepository roomRepository = Mock()
    ConferenceRoomBookingRepository bookingRepository = Mock()

    def "test releaseExpiredBookings"() {
        given:
        ConferenceRoomReleaseScheduler scheduler = new ConferenceRoomReleaseScheduler(roomRepository, bookingRepository)
        ConferenceRoomBooking booking = new ConferenceRoomBooking()
        booking.setId(1L)
        booking.setRoomReleased(false)
        booking.setEndTime(LocalDateTime.now().minusMinutes(1))
        ConferenceRoom room = new ConferenceRoom()
        booking.setRoom(room)

        when:
        bookingRepository.findByIsRoomReleasedFalseAndEndTimeBetween(_, _) >> [booking]
        scheduler.releaseExpiredBookings()

        then:
        1 * bookingRepository.save(booking)
        1 * roomRepository.save(booking.getRoom())
    }
}