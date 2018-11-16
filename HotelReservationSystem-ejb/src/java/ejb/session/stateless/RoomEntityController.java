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
import java.util.logging.Level;
import java.util.logging.Logger;
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
//    specifically for checkin checkout
    public void updateRoom(RoomEntity room) 
    {
        try {
            RoomEntity updateRoom = retrieveRoomById(room.getRoomId());
            updateRoom.setOccupied(room.getOccupied());
        } catch (RoomNotFoundException ex) {
            Logger.getLogger(RoomEntityController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void updateRoom(RoomEntity room, RoomTypeEntity roomType) 
    {
        try {
            RoomTypeEntity oldRoomType = roomTypeEntityController.retrieveRoomTypeByName(room.getRoomTypeEntity().getName());
            RoomTypeEntity newRoomType = roomTypeEntityController.retrieveRoomTypeByName(roomType.getName());
            RoomEntity roomToUpdate = retrieveRoomById(room.getRoomId());
            oldRoomType.getRoomEntities().remove(roomToUpdate);
            newRoomType.getRoomEntities().add(roomToUpdate);
            roomToUpdate.setRoomNumber(room.getRoomNumber());
            roomToUpdate.setRoomTypeEntity(newRoomType);
        } catch (RoomTypeNotFoundException ex) {
        
        } catch (RoomNotFoundException ex){
            
        }
    }
    
    @Override
    public RoomEntity retrieveRoomByNumber(String roomNumber) throws RoomNotFoundException
    {
        Query query = em.createQuery("SELECT r FROM RoomEntity r WHERE r.roomNumber = :inRoomNumber");
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
        Query query = em.createQuery("SELECT r FROM RoomEntity r WHERE r.roomId = :inRoomId");
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
        Query query = em.createQuery("SELECT r FROM RoomEntity r WHERE r.roomTypeEntity.roomTypeId = :inRoomTypeId AND r.allocated = FALSE AND r.occupied = FALSE AND r.disabled = FALSE");
        query.setParameter("inRoomTypeId", roomTypeId);
         
        List<RoomEntity> rooms = query.getResultList();
        
        if (rooms == null || rooms.isEmpty()){
            return null; 
        }

        try {
            RoomEntity roomToAllocate = retrieveRoomById(rooms.get(0).getRoomId());
            roomToAllocate.setAllocated(Boolean.TRUE);
            return roomToAllocate;
        } catch (RoomNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }
}
