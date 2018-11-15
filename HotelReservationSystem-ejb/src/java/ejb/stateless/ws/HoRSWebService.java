/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless.ws;

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
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import util.exception.InvalidLoginCredentialException;
import util.exception.PartnerNotFoundException;
import util.exception.ReservationNotFoundException;
import util.exception.RoomTypeNotFoundException;

/**
 *
 * @author Wai Kin
 */
@WebService(serviceName = "HoRSWebService")
@Stateless()
public class HoRSWebService {

    @EJB(name = "ReservationEntityControllerLocal")
    private ReservationEntityControllerLocal reservationEntityController;

    @EJB(name = "RoomTypeEntityControllerLocal")
    private RoomTypeEntityControllerLocal roomTypeEntityController;

    @EJB(name = "PartnerEntityControllerLocal")
    private PartnerEntityControllerLocal partnerEntityController;

    public List<RoomTypeEntity> retrieveAllRoomTypes(){
        return roomTypeEntityController.retrieveAllRoomTypes();
    }
    
    public List<Date> checkAvailability(Date checkInDate, Date checkOutDate, RoomTypeEntity roomType){
        return roomTypeEntityController.checkAvailability(checkInDate, checkOutDate, roomType);
    }
    
    public BigDecimal calculateTotalAmount(String roomTypeName, Date checkInDate, Date checkOutDate){
        return reservationEntityController.calculateTotalAmount(roomTypeName, checkInDate, checkOutDate);
    }
    
    public void allocateRoomManually(RoomTypeEntity roomType){
    }
    
    public ReservationEntity createNewReservation(CustomerEntity customerEntity, RoomTypeEntity roomTypeEntity, EmployeeEntity employeeEntity, PartnerEntity partnerEntity, Date checkInDate, Date checkOutDate){
        return reservationEntityController.createNewReservation(customerEntity, roomTypeEntity, employeeEntity, partnerEntity, checkInDate, checkOutDate);
    }
    
    public ReservationEntity retrieveReservationDetails(Long reservationId, PartnerEntity partnerEntity) throws ReservationNotFoundException{
        return reservationEntityController.retrieveReservationDetails(reservationId, partnerEntity);
    }
    
    public List<ReservationEntity> retrieveAllReservations(Long partnerId) throws ReservationNotFoundException{
        return partnerEntityController.retrieveAllReservations(partnerId);
    }
    
    public PartnerEntity partnerLogin(String username, String password){
        try {
            return partnerEntityController.partnerLogin(username, password);
        } catch (InvalidLoginCredentialException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public PartnerEntity retrievePartnerByUsername(String username){
        try {
        return partnerEntityController.retrievePartnerByUsername(username);
        } catch (PartnerNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public RoomTypeEntity retrieveRoomTypeByName(String roomTypeName) throws RoomTypeNotFoundException{
        return roomTypeEntityController.retrieveRoomTypeByName(roomTypeName);
    }
    
    public List<ReservationEntity> retrieveAllReservationsByPartnerId(Long partnerId){
        return reservationEntityController.retrieveAllReservationsByPartnerId(partnerId);
    }
}
