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
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import util.exception.ReservationDoesNotMatchException;
import util.exception.ReservationNotFoundException;
import util.exception.RoomNotFoundException;

/**
 *
 * @author Lance
 */
public interface ReservationEntityControllerRemote {
    
    public ReservationEntity createNewReservation(CustomerEntity customerEntity, RoomTypeEntity roomTypeEntity, EmployeeEntity employeeEntity, PartnerEntity partnerEntity, Date checkInDate, Date checkOutDate, Integer numRooms);
    
    public ReservationEntity retrieveReservationDetails(Long reservationId, CustomerEntity customerEntity) throws ReservationNotFoundException;

    public List<ReservationEntity> retrieveAllReservationsByCustomerId(Long customerId);

    public BigDecimal calculateTotalAmount(String roomTypeName, Date checkInDate, Date checkOutDate, Integer numRooms);

    public ReservationEntity retrieveReservationById(Long reservationId);

    public void checkOutGuest(Long reservationId) throws ReservationDoesNotMatchException;

    public List<ReservationEntity> retrieveAllReservationsForToday();

    public List<String> checkInGuest(Long reservationId) throws RoomNotFoundException;

    public void allocateRoomManually(ReservationEntity reservation, RoomTypeEntity roomType);
    
}
