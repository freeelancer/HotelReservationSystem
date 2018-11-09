/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomEntity;
import entity.RoomTypeEntity;
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
            RoomEntity roomToUpdate = retrieveRoomTypeById(room.getRoomId());
            roomToUpdate.getRoomTypeEntity().getRoomEntities().remove(roomToUpdate);
            RoomTypeEntity roomTypeNew = roomTypeEntityController.retrieveRoomTypeByName(room.getRoomTypeEntity().getName());
            roomTypeNew.getRoomEntities().add(roomToUpdate);
            roomToUpdate.setRoomNumber(room.getRoomNumber());
        } catch (RoomTypeNotFoundException ex) {
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

    private RoomEntity retrieveRoomTypeById(Long roomId) 
    {
        Query query = em.createQuery("SELECT r FROM RoomEntity r WHERE r.Id = :inRoomId");
        query.setParameter("inRoomId", roomId);
        return (RoomEntity)query.getSingleResult();
    }
    
    @Override
    public void deleteRoom(RoomEntity room) throws RoomIsUsedException, RoomAlreadyDisabledException{
        RoomEntity roomToDelete = retrieveRoomById(room.getRoomId());
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

    private RoomEntity retrieveRoomById(Long roomId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
