package com.driveit.driveit.reservationcarpooling;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationCarpoolingRepository extends JpaRepository<ReservationCarpooling, Integer> {

    public ReservationCarpooling findByCarpoolingIdAndCollaboratorId(int carpoolingId, int collaboratorId);
}
