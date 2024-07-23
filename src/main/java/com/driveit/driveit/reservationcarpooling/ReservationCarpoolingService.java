package com.driveit.driveit.reservationcarpooling;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationCarpoolingService {

    @Autowired
    private ReservationCarpoolingRepository reservationCarpoolingRepository;

    public void save(ReservationCarpooling reservationCarpooling) {
        reservationCarpoolingRepository.save(reservationCarpooling);
    }

    public void delete(ReservationCarpooling reservationCarpooling) {
        reservationCarpoolingRepository.delete(reservationCarpooling);
    }


    public ReservationCarpooling findByCarpoolingIdAndCollaboratorId(int carpoolingId, int collaboratorId) {
        return reservationCarpoolingRepository.findByCarpoolingIdAndCollaboratorId(carpoolingId, collaboratorId);
    }

}
