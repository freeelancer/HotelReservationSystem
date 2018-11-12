/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

/**
 *
 * @author Lance
 */
@Stateless
public class AllocationTimerSessionBean implements AllocationTimerSessionBeanRemote, AllocationTimerSessionBeanLocal {

    @EJB
    private RoomEntityControllerLocal roomEntityController;

    @Schedule(dayOfWeek = "Mon-Sun/1", month = "*", hour = "2", dayOfMonth = "*", year = "*", minute = "*", second = "0")
    @Override
    public void allocateAllRooms() {
        System.out.println("Timer event: " + new Date());
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
