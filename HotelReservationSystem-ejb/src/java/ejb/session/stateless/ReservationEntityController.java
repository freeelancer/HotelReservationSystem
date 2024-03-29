/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CustomerEntity;
import entity.DateEntity;
import entity.EmployeeEntity;
import entity.PartnerEntity;
import entity.ReservationEntity;
import entity.RoomEntity;
import entity.RoomRateEntity;
import entity.RoomTypeEntity;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import util.exception.ReservationDoesNotMatchException;
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

    @EJB
    private DateEntityControllerLocal dateEntityController;
    

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
    public ReservationEntity createNewReservation(CustomerEntity customerEntity, RoomTypeEntity roomTypeEntity, EmployeeEntity employeeEntity, PartnerEntity partnerEntity, Date checkInDate, Date checkOutDate, Integer numRooms){
        
        ReservationEntity reservationEntity = new ReservationEntity();
        
        reservationEntity.setCustomerEntity(customerEntity);
        reservationEntity.setRoomTypeEntity(roomTypeEntity);
        reservationEntity.setEmployeeEntity(employeeEntity);
        reservationEntity.setPartnerEntity(partnerEntity);
        reservationEntity.setCheckInDate(checkInDate);
        reservationEntity.setCheckOutDate(checkOutDate);
        reservationEntity.setNumRooms(numRooms);
       
        reservationEntity.setTotalAmount(calculateTotalAmount(roomTypeEntity.getName(), checkInDate, checkOutDate,numRooms)); // hardcoded for now
        
        CustomerEntity customerToUpdate = new CustomerEntity();
        
        try {
            customerToUpdate = customerEntityController.retrieveCustomerByUsername(customerEntity.getUsername());
        } catch (CustomerNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        
        em.persist(reservationEntity);
        em.flush();
        em.refresh(reservationEntity);
        
//        adding the number reserved into the Date Entity
        List<DateEntity> dateEntitys = dateEntityController.retrieveAllDateEntitysForRoomTypeId(reservationEntity.getRoomTypeEntity().getRoomTypeId());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkInDate);
        for(Date curr = checkInDate;curr.before(checkOutDate);)
        {
            for(DateEntity dateEntity:dateEntitys)
            {
                if(dateEntity.getDateTime().equals(curr))
                {
                    dateEntity.setNumReserved(dateEntity.getNumReserved()+numRooms);
                }
                if(dateEntity.getDateTime().equals(checkOutDate)||dateEntity.getDateTime().after(curr))
                {
                    break;
                }
            }
            calendar = Calendar.getInstance();
            calendar.setTime(curr);
            calendar.add(Calendar.DATE, 1);
            curr = calendar.getTime();
        }
        
        List<ReservationEntity> reservations = customerToUpdate.getReservationEntities();
        reservations.add(reservationEntity);
        customerToUpdate.setReservationEntities(reservations);
        customerEntityController.updateCustomer(customerToUpdate);
        
        return reservationEntity;        
    }

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
    public ReservationEntity retrieveReservationDetails(Long reservationId, PartnerEntity partnerEntity) throws ReservationNotFoundException {
 
        ReservationEntity reservationEntity = em.find(ReservationEntity.class, reservationId);
        
        if (reservationEntity != null){
            System.out.println("Reservation Found!");
            if (reservationEntity.getPartnerEntity().getPartnerId().equals(partnerEntity.getPartnerId())){
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

        Query query = em.createQuery("SELECT r FROM ReservationEntity WHERE r.customerId = :inCustomerId");
        query.setParameter("inCustomerId", customerId);
        
        return query.getResultList();
        
    }
    
    @Override
    public List<ReservationEntity> retrieveAllReservationsByPartnerId(Long partnerId){

        Query query = em.createQuery("SELECT r FROM ReservationEntity WHERE r.partnerId = :inPartnerId");
        query.setParameter("inPartnerId", partnerId);
        
        return query.getResultList();
        
    }
    
    // Doesn't work
    @Override
    public BigDecimal calculateTotalAmount(String roomTypeName, Date checkInDate, Date checkOutDate, Integer numRooms) {
        
        RoomTypeEntity roomType = new RoomTypeEntity();
        List<RoomRateEntity> roomRates = new ArrayList<RoomRateEntity>();
                
        try {
            roomType = roomTypeEntityController.retrieveRoomTypeByName(roomTypeName);
            
        } catch (RoomTypeNotFoundException e) {
            e.printStackTrace();
        }
        roomRates = roomType.getRoomRateEntities();               
        Calendar calendar = Calendar.getInstance();
        Date start = checkInDate;
        Date end = checkOutDate;

        BigDecimal sum = BigDecimal.ZERO;
        
        boolean condition=false;

        for(Date current = start; current.before(end); ){
            for(RoomRateEntity roomRate:roomRates){
                if(roomRate.getRateTypeEnum().equals(RateTypeEnum.PEAK) && roomRate.getValidityPeriod()!=null){
                    if(roomRate.getValidityPeriod().contains(current)){
                        sum = sum.add(roomRate.getRatePerNight());
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
                        sum = sum.add(roomRate.getRatePerNight());
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
                    sum = sum.add(roomRate.getRatePerNight());
                    calendar.setTime(current);
                    calendar.add(Calendar.DATE, 1);
                    current = calendar.getTime();
                    break;
                }
            }
        }
        return sum.multiply(new BigDecimal(numRooms));
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
        Query query = em.createQuery("SELECT r FROM ReservationEntity r WHERE r.reservationId = :inReservationId");
        query.setParameter("inReservationId", reservationId);
        
        return (ReservationEntity)query.getSingleResult();
    }
    
    @Override
    public List<String> checkInGuest(Long reservationId) throws RoomNotFoundException {
        ReservationEntity reservation = retrieveReservationById(reservationId);
        RoomEntity room = new RoomEntity();
        List<String> roomsCheckedIn = new ArrayList<String>();
        for(RoomEntity roomToCheckIn:reservation.getRoomEntitys())
        {
            room = roomEntityController.retrieveRoomById(roomToCheckIn.getRoomId());
            room.setOccupied(Boolean.TRUE);
            roomEntityController.updateRoom(room);
            roomsCheckedIn.add(roomToCheckIn.getRoomNumber());
            if (room == null){
                throw new RoomNotFoundException("Room "+roomToCheckIn.getRoomId()+" not allocated!");
            }
        }
        
        
        
        return roomsCheckedIn;
    }
    
    @Override
    public void checkOutGuest(Long reservationId) throws ReservationDoesNotMatchException{
        ReservationEntity reservation = retrieveReservationById(reservationId);
        List<RoomEntity> roomsToCheckOut = reservation.getRoomEntitys();
        try{
            for(RoomEntity roomToCheckOut:roomsToCheckOut)
            {
                if(roomToCheckOut.getReservationEntity().getReservationId()==reservationId)
                {
                    roomToCheckOut = roomEntityController.retrieveRoomById(roomToCheckOut.getRoomId());
                    roomToCheckOut.setOccupied(Boolean.FALSE);
                    roomEntityController.updateRoom(roomToCheckOut);
                    if (roomToCheckOut == null){
                        throw new RoomNotFoundException("Room "+roomToCheckOut.getRoomId()+" not there!");
                    }
                }
                else{
                    throw new ReservationDoesNotMatchException("Reservation ID does not match with that of Room!");
                }
            }
        } catch (RoomNotFoundException e) {
            e.printStackTrace();
        }        
    }
    
    @Override
    public List<ReservationEntity> retrieveAllReservations(){
        Query query = em.createQuery("SELECT r FROM ReservationEntity r");
        
        return query.getResultList();
    }
    
    @Override
    public void allocateRoomManually(ReservationEntity reservation, RoomTypeEntity roomType){
        ReservationEntity reservationM = retrieveReservationById(reservation.getReservationId());
        for(int i=0;i<reservationM.getNumRooms();i++)
        {
            RoomEntity roomToAllocate = roomEntityController.retrieveAvailableRoomByRoomType(roomType);
            roomToAllocate.setAllocated(Boolean.TRUE);
            reservationM.getRoomEntitys().add(roomToAllocate);
        }
    }
    
    @Override
    public List<ReservationEntity> retrieveAllReservationsForToday()
    {
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date today = df.parse(df.format(new Date()));
            Query query = em.createQuery("SELECT r FROM ReservationEntity r WHERE r.checkInDate = :inCheckInDate");
            query.setParameter("inCheckInDate", today, TemporalType.DATE);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public void updateReservation(ReservationEntity reservation){
        Query query = em.createQuery("SELECT r FROM ReservationEntity r WHERE r.reservationId = :inReservationId");
        query.setParameter("inReservationId", reservation.getReservationId());
        
        ReservationEntity reservationToUpdate = (ReservationEntity)query.getSingleResult();
        
        reservationToUpdate.setRoomEntitys(reservation.getRoomEntitys());
        reservationToUpdate.setRoomTypeEntity(reservation.getRoomTypeEntity());
    }
}
