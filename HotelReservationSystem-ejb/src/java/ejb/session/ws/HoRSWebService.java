/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.ws;

import ejb.session.stateless.PartnerEntityControllerLocal;
import ejb.session.stateless.ReservationEntityControllerLocal;
import ejb.session.stateless.RoomTypeEntityControllerLocal;
import entity.CustomerEntity;
import entity.EmployeeEntity;
import entity.PartnerEntity;
import entity.ReservationEntity;
import entity.RoomTypeEntity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javafx.util.Pair;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import util.exception.InvalidLoginCredentialException;
import util.exception.ReservationNotFoundException;
import util.exception.RoomTypeNotFoundException;

/**
 *
 * @author Wai Kin
 */
@WebService(serviceName = "HoRSWebService")
@Stateless()
public class HoRSWebService {

    @EJB(name = "PartnerEntityControllerLocal")
    private PartnerEntityControllerLocal partnerEntityController;

    @EJB(name = "ReservationEntityControllerLocal")
    private ReservationEntityControllerLocal reservationEntityController;

    @EJB(name = "RoomTypeEntityControllerLocal")
    private RoomTypeEntityControllerLocal roomTypeEntityController;

    public PartnerEntity partnerLogin(String username, String password){
        try {
            return partnerEntityController.partnerLogin(username, password);
        } catch (InvalidLoginCredentialException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Pair<RoomTypeEntity,Integer>> searchRoomTypeByDate(Date date){
        return roomTypeEntityController.searchRoomTypesByDate(date);
    }
    
    public List<RoomTypeEntity> retrieveAllRoomTypes(){
        return roomTypeEntityController.retrieveAllRoomTypes();
    }
    
    public RoomTypeEntity retrieveRoomTypeByName(String name){
        try {
            return roomTypeEntityController.retrieveRoomTypeByName(name);
        } catch (RoomTypeNotFoundException e) {
            e.printStackTrace();
            return null;
        } 
    }
    
    public List<Date> checkAvailability(Date checkInDate, Date checkOutnDate, RoomTypeEntity roomType, Integer numRooms){
        return roomTypeEntityController.checkAvailability(checkInDate, checkInDate, roomType, numRooms);
    }
    
    public BigDecimal calculateTotalAmount(String roomType, Date checkInDate, Date checkOutDate, Integer numRooms){
        return reservationEntityController.calculateTotalAmount(roomType, checkInDate, checkOutDate, numRooms);
    }
    
    public ReservationEntity createNewReservation(CustomerEntity customerEntity, RoomTypeEntity roomTypeEntity, EmployeeEntity employeeEntity, PartnerEntity partnerEntity, Date checkInDate, Date checkOutDate, Integer numRooms){
        return reservationEntityController.createNewReservation(customerEntity, roomTypeEntity, employeeEntity, partnerEntity, checkInDate, checkOutDate, Integer.BYTES);
    }
    
    public void allocateManually(ReservationEntity reservation, RoomTypeEntity roomType){
        reservationEntityController.allocateRoomManually(reservation, roomType);
    }
    
    public ReservationEntity retrieveReservationDetails(Long reservationId, PartnerEntity partnerEntity){
        try {
            return reservationEntityController.retrieveReservationDetails(reservationId, partnerEntity);
        } catch (ReservationNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<ReservationEntity> retrieveAllReservations(Long partnerId){
        return reservationEntityController.retrieveAllReservationsByPartnerId(partnerId);
    }
}
