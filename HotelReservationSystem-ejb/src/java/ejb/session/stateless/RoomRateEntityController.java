/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomRateEntity;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.DeleteRoomRateException;
import util.exception.RoomRateNotFoundException;

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
    
    @Override
    public RoomRateEntity createNewRoomRate(RoomRateEntity roomRate){
        em.persist(roomRate);
        em.flush();
        em.refresh(roomRate);
        
        return roomRate;
    }
    
    @Override
    public RoomRateEntity retrieveRoomRateById(Long roomRateId) throws RoomRateNotFoundException{
        RoomRateEntity roomRate = em.find(RoomRateEntity.class, roomRateId);
        
        if (roomRate != null){
            return roomRate;
        } else {
            throw new RoomRateNotFoundException("Room Rate " + roomRateId + " does not exis!");
        }
    }
    
    @Override
    public List<RoomRateEntity> retrieveAllRoomRates(){
        Query query = em.createQuery("SELECT rr FROM RoomRateEntity rr");
        return query.getResultList();
    }
    
    @Override
    public void updateRoomRate(RoomRateEntity roomRate){
        RoomRateEntity roomRateToUpdate = new RoomRateEntity();
        try {
            roomRateToUpdate = retrieveRoomRateById(roomRate.getRoomRateId());
        } catch (RoomRateNotFoundException e){
            System.out.println(e.getMessage());
        }
        roomRateToUpdate.setName(roomRate.getName());
        roomRateToUpdate.setRatePerNight(roomRate.getRatePerNight());
        roomRateToUpdate.setRateTypeEnum(roomRate.getRateTypeEnum());
        roomRateToUpdate.setValidityPeriod(roomRate.getValidityPeriod());
    }
    
    @Override
    public void deleteRoomRate(Long roomRateId) throws DeleteRoomRateException{
         RoomRateEntity roomRateToDelete = new RoomRateEntity();
        try {
            roomRateToDelete = retrieveRoomRateById(roomRateId);
        } catch (RoomRateNotFoundException e){
            System.out.println(e.getMessage());
        }
        if(roomRateToDelete.getReservationEntities().isEmpty()){
            em.remove(roomRateToDelete);
        } else {
            throw new DeleteRoomRateException("Room rate ID " + roomRateId + "is associated with existing reservation(s) and cannot be deleted!");
        }
    }
}
