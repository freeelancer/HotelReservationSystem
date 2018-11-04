/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.DateEntity;
import entity.RoomTypeEntity;
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
import util.exception.RoomTypeNotFoundException;

/**
 *
 * @author Lance
 */
@Stateless
@Local(RoomTypeEntityControllerLocal.class)
@Remote(RoomTypeEntityControllerRemote.class)
public class RoomTypeEntityController implements RoomTypeEntityControllerRemote, RoomTypeEntityControllerLocal 
{

    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;
    
    @Override
    public RoomTypeEntity createNewRoomType(RoomTypeEntity newRoomTypeEntity)
    {
        em.persist(newRoomTypeEntity);
        em.flush();
        return newRoomTypeEntity;
    }
    
    @Override
    public RoomTypeEntity retrieveRoomTypeByName(String roomTypeName) throws RoomTypeNotFoundException
    {
        Query query = em.createQuery("SELECT r FROM RoomTypeEntity r WHERE r.name = :inRoomTypeName");
        query.setParameter("inRoomTypeName", roomTypeName);
        
        try
        {
            return (RoomTypeEntity)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new RoomTypeNotFoundException("Room Type " + roomTypeName + " does not exist!");
        }
    }
    
    @Override
    public void updateRoomType(RoomTypeEntity roomType)
    {   
        RoomTypeEntity roomTypeToUpdate = retrieveRoomTypeById(roomType.getRoomTypeId());
        roomTypeToUpdate.setAmenities(roomType.getAmenities());
        roomTypeToUpdate.setBedTypeEnum(roomType.getBedTypeEnum());
        roomTypeToUpdate.setCapacity(roomType.getCapacity());
        roomTypeToUpdate.setDescription(roomType.getDescription());
        roomTypeToUpdate.setName(roomType.getName()); 
        roomTypeToUpdate.setSize(roomType.getSize());
    }
    
    private RoomTypeEntity retrieveRoomTypeById(Long roomTypeId)
    {
        RoomTypeEntity roomType = em.find(RoomTypeEntity.class, roomTypeId);
        return roomType;
    }
    
    @Override
    public String deleteRoomType(RoomTypeEntity roomType)
    {
        RoomTypeEntity roomTypeToDelete = retrieveRoomTypeById(roomType.getRoomTypeId());
        if(roomTypeToDelete.getRoomEntities().size()==0)
        {
            em.remove(roomTypeToDelete);
            return("Room Type "+roomType.getName()+" deleted from database\n");
        }
        else if(roomTypeToDelete.getUsed()==Boolean.TRUE)
        {
            roomTypeToDelete.setUsed(Boolean.FALSE);
            return("Room Type "+roomType.getName()+" currently still has rooms with such Room Type. As such it is disabled from further use but not deleted\n");
        }
        else
        {
            return("Room Type "+roomType.getName()+" currently still has rooms with such Room Type and is already disabled\n");
        }
    }
    
    @Override
    public List<RoomTypeEntity> retrieveAllRoomTypes()
    {
        Query query = em.createQuery("SELECT r FROM RoomTypeEntity r");
        return query.getResultList();
    }
    
    public List<Date> checkAvailability(Date checkInDate, Date checkOutDate){
        return new ArrayList<Date>();
    }
}
