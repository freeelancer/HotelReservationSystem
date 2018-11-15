/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomEntity;
import entity.RoomTypeEntity;
import java.util.ArrayList;
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
import util.exception.RoomAlreadyDisabledException;
import util.exception.RoomIsUsedException;
import util.exception.RoomNotFoundException;
import util.exception.RoomTypeNotFoundException;

/**
 *
 * @author Lance
 */
@Stateless
@Local(RoomEntityControllerLocal.class)
@Remote(RoomEntityControllerRemote.class)
public class RoomEntityController implements RoomEntityControllerRemote, RoomEntityControllerLocal 
{

    @EJB
    private RoomTypeEntityControllerLocal roomTypeEntityController;

    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;

    public RoomEntityController() {
    }
    
    @Override
    public void createNewRoom(RoomEntity room, String roomTypeName) throws RoomTypeNotFoundException
    {
        try {
            RoomTypeEntity roomType = roomTypeEntityController.retrieveRoomTypeByName(roomTypeName);
            room.setRoomTypeEntity(roomType);
            roomType.getRoomEntities().add(room);
            em.persist(room);
        } catch (RoomTypeNotFoundException ex) {
            throw new RoomTypeNotFoundException(ex.getMessage());
        }       
    }
    
    @Override
    public void updateRoom(RoomEntity room) 
    {
        try {
            RoomEntity roomToUpdate = retrieveRoomById(room.getRoomId());
            roomToUpdate.getRoomTypeEntity().getRoomEntities().remove(roomToUpdate);
            RoomTypeEntity roomTypeNew = roomTypeEntityController.retrieveRoomTypeByName(room.getRoomTypeEntity().getName());
            roomTypeNew.getRoomEntities().add(roomToUpdate);
            roomToUpdate.setRoomNumber(room.getRoomNumber());
        } catch (RoomTypeNotFoundException ex) {
        
        } catch (RoomNotFoundException ex){
            
        }
    }
    
    @Override
    public RoomEntity retrieveRoomByNumber(String roomNumber) throws RoomNotFoundException
    {
        Query query = em.createQuery("SELECT r FROM RoomEntity r WHERE r.name = :inRoomNumber");
        query.setParameter("inRoomNumber", roomNumber);
        
        try
        {
            return (RoomEntity)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new RoomNotFoundException("Room " + roomNumber + " does not exist!");
        }
    }

    @Override
    public RoomEntity retrieveRoomById(Long roomId) throws RoomNotFoundException
    {
        Query query = em.createQuery("SELECT r FROM RoomEntity r WHERE r.Id = :inRoomId");
        query.setParameter("inRoomId", roomId);
        RoomEntity room = (RoomEntity)query.getSingleResult();
        if(room != null){
            return room;
        } else {
            throw new RoomNotFoundException("No room allocated!");
        }        
    }
    
    @Override
    public void deleteRoom(RoomEntity room) throws RoomIsUsedException, RoomAlreadyDisabledException
    {
        RoomEntity roomToDelete = new RoomEntity();
        try{
            roomToDelete = retrieveRoomById(room.getRoomId());
        } catch (RoomNotFoundException e) {
            e.printStackTrace();
        }
//        change if clause if necessary
        if(roomToDelete.getAllocated().equals(Boolean.FALSE)&&roomToDelete.getOccupied().equals(Boolean.FALSE))
        {
            em.remove(roomToDelete);
        }
        else if(roomToDelete.getDisabled()==Boolean.FALSE)
        {
            roomToDelete.setDisabled(Boolean.TRUE);
            throw new RoomIsUsedException("Room "+room.getRoomNumber()+" currently is still used. As such it is disabled from further use but not deleted\n");
        }
        else
        {
            throw new RoomAlreadyDisabledException("Room "+room.getRoomNumber()+" currently is still used and is already disabled\n");
        }
        
    }  
    
    @Override
    public List<RoomEntity> retrieveAllRooms()
    {
        Query query = em.createQuery("SELECT r FROM RoomEntity r");
        return query.getResultList();
    }
    
    @Override
    public RoomEntity retrieveAvailableRoomByRoomType(RoomTypeEntity roomType){

        Long roomTypeId = roomType.getRoomTypeId();
        Query query = em.createQuery("SELECT r FROM RoomEntity r WHERE r.roomTypeEntity.roomTypeId = :inRoomTypeId");
        query.setParameter("inRoomTypeId", roomTypeId);

        List<RoomEntity> rooms = query.getResultList();

        if (rooms != null){
            return null; 
        }
        
        RoomEntity roomToAllocate = new RoomEntity();
                
        try {
            roomToAllocate = retrieveRoomById(rooms.get(0).getRoomId());
        } catch (RoomNotFoundException e){
        
        }
        
        return roomToAllocate;
    }
    
}
