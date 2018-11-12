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
import entity.RoomRateEntity;
import entity.RoomTypeEntity;
import java.math.BigDecimal;
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
import util.enumeration.RateTypeEnum;
import util.exception.CustomerNotFoundException;
import util.exception.ReservationNotFoundException;
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
       
        reservationEntity.setTotalAmount(calculateTotalAmount(roomTypeEntity, checkInDate, checkOutDate)); // hardcoded for now
        
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

    @Override
    public BigDecimal calculateTotalAmount(RoomTypeEntity roomTypeEntity, Date checkInDate, Date checkOutDate) {
        
        RoomTypeEntity roomType = new RoomTypeEntity();
                
        try {
            roomType = roomTypeEntityController.retrieveRoomTypeByName(roomTypeEntity.getName());
        } catch (RoomTypeNotFoundException e) {
            e.printStackTrace();
        } 
        
        List<RoomRateEntity> roomRates = roomType.getRoomRateEntities();
                
        Date start = new Date();
        Date end = new Date();
        
        Calendar calendar = Calendar.getInstance();
        
        try {
            start = checkInDate;
            end = checkOutDate;
            calendar.setTime(end);
            calendar.add(Calendar.DATE, 1);
            end = calendar.getTime();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        BigDecimal sum = new BigDecimal(0.00);
        
        // Prevent crashing
        if (roomRates==null){
            System.out.println("There are no room rates!");
            return sum;
        }
        
        // For checking
        System.out.println(roomRates.size());
        for (RoomRateEntity roomRate:roomRates){
            System.out.println(roomRate.getName());
        }
        
        cycleDates: for(Date current = start; current.before(end); ){
            
            calendar.setTime(current);
            calendar.add(Calendar.DATE, 1);
            current = calendar.getTime();
            
            for(RoomRateEntity roomRate:roomRates){
                if(roomRate.getRateTypeEnum().equals(RateTypeEnum.PEAK) && roomRate.getValidityPeriod()!=null){
                    if(roomRate.getValidityPeriod().contains(current)){
                        sum.add(roomRate.getRatePerNight());
                        continue cycleDates;
                    }
                }
            }
            for(RoomRateEntity roomRate:roomRates){
                if(roomRate.getRateTypeEnum().equals(RateTypeEnum.PROMOTION) && roomRate.getValidityPeriod()!=null){
                   if(roomRate.getValidityPeriod().contains(current)){
                        sum.add(roomRate.getRatePerNight());
                        continue cycleDates;
                    }
                }
            }
            for(RoomRateEntity roomRate:roomRates){
                if(roomRate.getRateTypeEnum().equals(RateTypeEnum.NORMAL)){
                    sum.add(roomRate.getRatePerNight());
                    continue cycleDates;
                }
            }
        }
        return sum;
    }
}
