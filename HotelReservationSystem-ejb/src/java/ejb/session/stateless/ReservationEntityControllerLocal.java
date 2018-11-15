/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CustomerEntity;
import entity.EmployeeEntity;
import entity.PartnerEntity;
import entity.ReservationEntity;
import entity.RoomTypeEntity;
import java.util.Date;
import java.util.List;
import util.exception.ReservationNotFoundException;

/**
 *
 * @author Lance
 */
public interface ReservationEntityControllerLocal {

    public ReservationEntity createNewReservation(CustomerEntity customerEntity, RoomTypeEntity roomTypeEntity, EmployeeEntity employeeEntity, PartnerEntity partnerEntity, Date checkInDate, Date checkOutDate);
    
    public ReservationEntity retrieveReservationDetails(Long reservationId, CustomerEntity customerEntity) throws ReservationNotFoundException;

    public List<ReservationEntity> retrieveAllReservationsByCustomerId(Long customerId);

//    public void allocateRooms();

    public List<ReservationEntity> retrieveAllReservations();
    
    public List<ReservationEntity> retrieveAllReservationsForToday();
    
}
