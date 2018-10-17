/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

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
@Local(RoomRateEntityControllerLocal.class)
@Remote(RoomRateEntityControllerRemote.class)
public class RoomRateEntityController implements RoomRateEntityControllerRemote, RoomRateEntityControllerLocal 
{
    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;

}
