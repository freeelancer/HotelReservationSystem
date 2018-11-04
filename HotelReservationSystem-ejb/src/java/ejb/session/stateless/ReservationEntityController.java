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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.ReservationNotFoundException;

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
        reservationEntity.setTotalAmount(new BigDecimal(1000.00)); // hardcoded for now
        
        em.persist(reservationEntity);
        em.flush();
        
        return reservationEntity;        
    }
    
    // Logic not done
    @Override
    public ReservationEntity retrieveReservationDetails(Long reservationId, CustomerEntity customerEntity) throws ReservationNotFoundException {
        
        Query query = em.createQuery("SELECT r FROM ReservationEntity r WHERE r.reservationid = :inReservationId");
        query.setParameter("inReservationId", reservationId);
        
        ReservationEntity reservationEntity = new ReservationEntity();
        
        try
        {
            reservationEntity = (ReservationEntity)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new ReservationNotFoundException("Reservation " + reservationId + " does not exist!");
        }
        
        if (reservationEntity.getCustomerEntity().getCustomerId() == customerEntity.getCustomerId()){
            return reservationEntity;  
        } else {
            throw new ReservationNotFoundException("Reservation " + reservationId + " does not exist!");
        }
    }
    
    // Logic not done
    @Override
    public List<ReservationEntity> retrieveAllReservationsByCustomerId(Long customerId){
    
        List<ReservationEntity> reservationList = new ArrayList<ReservationEntity>();
        
        return reservationList;
    }
}
