/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomTypeEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    
    // Logic not done
    @Override
    public List<RoomTypeEntity> retrieveAllRoomTypes(){
        List<RoomTypeEntity> roomTypeList = new ArrayList<RoomTypeEntity>();
        return roomTypeList;
    }
    
    // Logic not done
    @Override
    public List<Date> checkAvailability(Date checkInDate, Date checkOutDate){
        
        List<Date> datesUnavailable = new ArrayList<Date>();
        
        return datesUnavailable;
    }
    
}
