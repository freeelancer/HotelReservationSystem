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
import entity.RoomEntity;
import entity.RoomRateEntity;
import entity.RoomTypeEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import util.enumeration.RateTypeEnum;
import util.exception.CustomerNotFoundException;
import util.exception.ReservationNotFoundException;
import util.exception.RoomNotFoundException;
import util.exception.RoomTypeNotFoundException;

/**
 *
 * @author Lance
 */
@Stateless
@Local(ReservationEntityControllerLocal.class)
@Remote(ReservationEntityControllerRemote.class)
public class ReservationEntityController implements ReservationEntityControllerRemote, ReservationEntityControllerLocal 
{
    

    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;
    
    @EJB
    CustomerEntityControllerLocal customerEntityController;
    
    @EJB
    RoomTypeEntityControllerLocal roomTypeEntityController;
    
    @EJB
    RoomEntityControllerLocal roomEntityController;
    
    // Logic not done
    @Override
    public ReservationEntity createNewReservation(CustomerEntity customerEntity, RoomTypeEntity roomTypeEntity, EmployeeEntity employeeEntity, PartnerEntity partnerEntity, Date checkInDate, Date checkOutDate){
        
        ReservationEntity reservationEntity = new ReservationEntity();
        
        reservationEntity.setCustomerEntity(customerEntity);
        reservationEntity.setRoomTypeEntity(roomTypeEntity);
        reservationEntity.setEmployeeEntity(employeeEntity);
        reservationEntity.setPartnerEntity(partnerEntity);
        reservationEntity.setCheckInDate(checkInDate);
        reservationEntity.setCheckOutDate(checkOutDate);
       
        reservationEntity.setTotalAmount(calculateTotalAmount(roomTypeEntity.getName(), checkInDate, checkOutDate)); // hardcoded for now
        
        CustomerEntity customerToUpdate = new CustomerEntity();
        
        try {
            customerToUpdate = customerEntityController.retrieveCustomerByUsername(customerEntity.getUsername());
        } catch (CustomerNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        
        em.persist(reservationEntity);
        em.flush();
        em.refresh(reservationEntity);
        
        List<ReservationEntity> reservations = customerToUpdate.getReservationEntities();
        reservations.add(reservationEntity);
        customerToUpdate.setReservationEntities(reservations);
        customerEntityController.updateCustomer(customerToUpdate);
        
        return reservationEntity;        
    }
    
    // Logic not done
    @Override
    public ReservationEntity retrieveReservationDetails(Long reservationId, CustomerEntity customerEntity) throws ReservationNotFoundException {
 
        ReservationEntity reservationEntity = em.find(ReservationEntity.class, reservationId);
        
        if (reservationEntity != null){
            System.out.println("Reservation Found!");
            if (reservationEntity.getCustomerEntity().getCustomerId().equals(customerEntity.getCustomerId())){
                return reservationEntity;  
            } else {
            throw new ReservationNotFoundException("Error 2: Reservation " + reservationId + " does not exist!");
            }
        } else {
            throw new ReservationNotFoundException("Error 1: Reservation " + reservationId + " does not exist!");
        }
    }
    
    @Override
    public List<ReservationEntity> retrieveAllReservationsByCustomerId(Long customerId){

        Query query = em.createQuery("SELECT r FROM ReservationEntity r");
        
        return query.getResultList();
        
    }
    
    // Doesn't work
    @Override
    public BigDecimal calculateTotalAmount(String roomTypeName, Date checkInDate, Date checkOutDate) {
        
        RoomTypeEntity roomType = new RoomTypeEntity();
        List<RoomRateEntity> roomRates = new ArrayList<RoomRateEntity>();
                
        try {
            roomType = roomTypeEntityController.retrieveRoomTypeByName(roomTypeName);
            roomRates = roomType.getRoomRateEntities();
        } catch (RoomTypeNotFoundException e) {
            e.printStackTrace();
        } 
                        
        Date start = new Date();
        Date end = new Date();

        Calendar calendar = Calendar.getInstance();
        start = checkInDate;
        end = checkOutDate;
        calendar.setTime(end);
        calendar.add(Calendar.DATE, 1);
        end = calendar.getTime();

        BigDecimal sum = new BigDecimal("0");
        sum.add(new BigDecimal("200")); // test
        
        boolean condition=false;

        for(Date current = start; current.before(end); ){
            for(RoomRateEntity roomRate:roomRates){
                if(roomRate.getRateTypeEnum() == RateTypeEnum.PEAK && roomRate.getValidityPeriod()!=null){
                    if(roomRate.getValidityPeriod().contains(current)){
                        sum.add(roomRate.getRatePerNight());
                        calendar.setTime(current);
                        calendar.add(Calendar.DATE, 1);
                        current = calendar.getTime();
                        condition=true;
                        break;
                    }
                }
            }
            if(condition){ continue;}
            for(RoomRateEntity roomRate:roomRates){
                if(roomRate.getRateTypeEnum() == RateTypeEnum.PROMOTION && roomRate.getValidityPeriod()!=null){
                   if(roomRate.getValidityPeriod().contains(current)){
                        sum.add(roomRate.getRatePerNight());
                        calendar.setTime(current);
                        calendar.add(Calendar.DATE, 1);
                        current = calendar.getTime();
                        condition=true;
                        break;
                    }
                }
            }
            if(condition){ continue;}
            for(RoomRateEntity roomRate:roomRates){
                if(roomRate.getRateTypeEnum() == RateTypeEnum.NORMAL){
                    sum.add(roomRate.getRatePerNight());
                    calendar.setTime(current);
                    calendar.add(Calendar.DATE, 1);
                    current = calendar.getTime();
                    break;
                }
            }
        }
        return sum;
    }
    
//    @Override
//    public void allocateRooms()
//    {
//        List<ReservationEntity> reservations = retrieveAllReservationByDate();
//        List<RoomTypeEntity> roomTypes = roomTypeEntityController.retrieveAllRoomTypes();
//        for(RoomTypeEntity roomType:roomTypes)
//        {
//            List<RoomTypeEntity> roomType.getName() = room
//        }
//    }

    private List<ReservationEntity> retrieveAllReservationByDate() 
    {
        Query query = em.createQuery("SELECT r FROM ReservationEntity WHERE s.checkInDate = :inDate");
        query.setParameter("today",new Date(),TemporalType.DATE);
        return query.getResultList();
    }
    
    @Override   
    public ReservationEntity retrieveReservationById(Long reservationId){
        ReservationEntity reservation = em.find(ReservationEntity.class, reservationId);
        return reservation;
    }
    
    @Override
    public String checkInGuest(Long reservationId){
        ReservationEntity reservation = retrieveReservationById(reservationId);
        RoomEntity room = new RoomEntity();
        try{
            room = roomEntityController.retrieveRoomById(reservation.getRoomEntity().getRoomId());
            room.setOccupied(Boolean.TRUE);
            roomEntityController.updateRoom(room);
        } catch (RoomNotFoundException e) {
            e.printStackTrace();
        }        
        return room.getRoomNumber();
    }
    
    @Override
    public void checkOutGuest(Long reservationId){
        ReservationEntity reservation = retrieveReservationById(reservationId);
         RoomEntity room = new RoomEntity();
        try{
            room = roomEntityController.retrieveRoomById(reservation.getRoomEntity().getRoomId());
            room.setOccupied(Boolean.FALSE);
            roomEntityController.updateRoom(room);
        } catch (RoomNotFoundException e) {
            e.printStackTrace();
        }        
    }
    
    @Override
    public List<ReservationEntity> retrieveAllReservations(){
        Query query = em.createQuery("SELECT r FROM ReservationEntity r");
        
        return query.getResultList();
    }
}
