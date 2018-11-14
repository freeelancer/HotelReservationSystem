/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ReservationEntity;
import entity.RoomRateEntity;
import entity.RoomTypeEntity;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import util.exception.DeleteRoomRateException;
import util.exception.RoomRateAlreadyDisabledException;
import util.exception.RoomRateIsUsedException;
import util.exception.RoomRateNotFoundException;
import util.exception.RoomTypeNotFoundException;

/**
 *
 * @author Lance
 */
@Stateless
@Local(RoomRateEntityControllerLocal.class)
@Remote(RoomRateEntityControllerRemote.class)
public class RoomRateEntityController implements RoomRateEntityControllerRemote, RoomRateEntityControllerLocal 
{
    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;
    
    @EJB
    RoomTypeEntityControllerLocal roomTypeController;
    
    @Override
    public RoomRateEntity createNewRoomRate(RoomRateEntity roomRate, String roomTypeName) throws RoomTypeNotFoundException
    {
        try{
            RoomTypeEntity roomTypeToUpdate = roomTypeController.retrieveRoomTypeByName(roomTypeName);
            roomRate.setRoomType(roomTypeToUpdate);
            em.persist(roomRate);
            em.flush();
            em.refresh(roomRate);
            roomTypeToUpdate.getRoomRateEntities().add(roomRate);
            return roomRate;
        }catch(RoomTypeNotFoundException ex){
            throw new RoomTypeNotFoundException();
        }  
    }
    
    @Override
    public RoomRateEntity createNewRoomRate(RoomRateEntity roomRate){
        try {
            em.persist(roomRate);
            em.flush();
            em.refresh(roomRate);
            RoomTypeEntity roomTypeToUpdate = roomTypeController.retrieveRoomTypeByName(roomRate.getRoomType().getName());
            List<RoomRateEntity> roomRates = roomTypeToUpdate.getRoomRateEntities();
            roomRates.add(roomRate);
            roomTypeToUpdate.setRoomRateEntities(roomRates);
            roomTypeController.updateRoomType(roomTypeToUpdate);
            return roomRate;
        } catch (RoomTypeNotFoundException ex) {
        }
        return roomRate;
    }
    
    private RoomRateEntity retrieveRoomRateById(Long roomRateId) {
        RoomRateEntity roomRate = em.find(RoomRateEntity.class, roomRateId);
        return roomRate;
    }
    
    @Override
    public RoomRateEntity retrieveRoomRateByName(String rateName) throws RoomRateNotFoundException
    {
        Query query = em.createQuery("SELECT rr FROM RoomRateEntity rr WHERE rr.name = :inName");
        query.setParameter("inName", rateName); 
        try
        {
            RoomRateEntity rate = (RoomRateEntity)query.getSingleResult();
            rate.getValidityPeriod().size();
            return rate;
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new RoomRateNotFoundException("Room Rate " + rateName + " does not exist!");
        }
    }

    
    @Override
    public List<RoomRateEntity> retrieveAllRoomRates(){
        Query query = em.createQuery("SELECT rr FROM RoomRateEntity rr");
        return query.getResultList();
    }
    
    @Override
    public RoomRateEntity updateRoomRate(RoomRateEntity roomRate,String roomTypeName)
    {
        try {
            RoomRateEntity roomRateToUpdate = retrieveRoomRateById(roomRate.getRoomRateId());
            RoomTypeEntity roomTypeUpdate = roomTypeController.retrieveRoomTypeByName(roomTypeName);
            RoomTypeEntity roomTypePrevious = roomTypeController.retrieveRoomTypeByName(roomRate.getRoomType().getName());
            roomRateToUpdate.setName(roomRate.getName());
            roomRateToUpdate.setRatePerNight(roomRate.getRatePerNight());
            roomRateToUpdate.setRateTypeEnum(roomRate.getRateTypeEnum());
            roomRateToUpdate.setValidityPeriod(roomRate.getValidityPeriod());
            roomRateToUpdate.setRoomType(roomTypeUpdate);
            em.persist(roomRateToUpdate);
            em.flush();
            em.refresh(roomRateToUpdate);
            roomTypePrevious.getRoomRateEntities().remove(roomRateToUpdate);
            roomTypeUpdate.getRoomRateEntities().add(roomRateToUpdate);
            return roomRateToUpdate;
        } catch (RoomTypeNotFoundException ex) {
        }
        return null;
    }
    
    public void deleteRoomRate(RoomRateEntity rate) throws RoomRateIsUsedException, RoomRateAlreadyDisabledException
    {
        RoomRateEntity roomRateToDelete = retrieveRoomRateById(rate.getRoomRateId());
        boolean used = checkTheFuture(roomRateToDelete);
        if(!used)
        {
            em.remove(roomRateToDelete);
        }
        else if(roomRateToDelete.getDisabled())
        {
            throw new RoomRateAlreadyDisabledException("Room Rate "+rate.getName()+" currently still has Reservation associated with it and is already disabled\n");        
        }
        else
        {
            roomRateToDelete.setDisabled(Boolean.TRUE);
            throw new RoomRateIsUsedException("Room Rate "+rate.getName()+" currently still has Reservation associated with it. As such it is disabled from further use but not deleted\n");
        }
    }
    
    private boolean checkTheFuture(RoomRateEntity rate)
    {
//        Query query = em.createNamedQuery("SELECT r FROM ReservationEntity r WHERE r.checkOutDate > :today");
//        query.setParameter("today", new Date(), TemporalType.DATE);
//        List<ReservationEntity> reservations= query.getResultList();
//        for(ReservationEntity reservation:reservations)
//        {
//            if(reservation.getRoomRateEntity().getName().equals(rate.getName()))
//            {
//                return true;
//            }
//        }
//        return false;
        return true;
    }
    
    @Override
    public void deleteRoomRate(Long roomRateId) throws DeleteRoomRateException{
        RoomRateEntity roomRateToDelete = new RoomRateEntity();
        roomRateToDelete = retrieveRoomRateById(roomRateId);
        if(roomRateToDelete.getReservations().isEmpty()){
            em.remove(roomRateToDelete);
        } else {
            throw new DeleteRoomRateException("Room rate ID " + roomRateId + "is associated with existing reservation(s) and cannot be deleted!");
        }
    }
}
