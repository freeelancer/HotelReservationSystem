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
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
        Query query = em.createQuery("SELECT rr FROM RoomRateEntity rr WHERE rr.roomRateId = :inRoomRateId");
        query.setParameter("inRoomRateId", roomRateId);
        
        try{
            return (RoomRateEntity)query.getSingleResult();
        } catch (NoResultException ex) {
            throw new RoomRateNotFoundException("Room Rate " + roomRateId + " does not exis!");
        }
    }
    
    @Override
    public List<RoomRateEntity> retrieveAllRoomRates(){
        
    }
    
    @Override
    public RoomRateEntity updateRoomRate(RoomRateEntity roomRate){
    
    }
    
    @Override
    public void deleteRoomRate(RoomRateEntity roomRate){
    
    }
}
