package com.mashreq.crb.repository;

import com.mashreq.crb.entity.RoomMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomMaintenanceRepository extends JpaRepository<RoomMaintenance, Long> {
}
