/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomEntity;
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
@Local(RoomEntityControllerLocal.class)
@Remote(RoomEntityControllerRemote.class)
public class RoomEntityController implements RoomEntityControllerRemote, RoomEntityControllerLocal 
{

    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;

    public RoomEntityController() {
    }
    
    @Override
    public RoomEntity createNewRoom(RoomEntity room)
    {
        em.persist(room);
        em.flush();
        return room;
    }
    
}
