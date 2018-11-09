/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horsreservationclient;

import ejb.session.stateless.CustomerEntityControllerRemote;
import ejb.session.stateless.EmployeeEntityControllerRemote;
import ejb.session.stateless.PartnerEntityControllerRemote;
import ejb.session.stateless.ReservationEntityControllerRemote;
import ejb.session.stateless.RoomEntityControllerRemote;
import ejb.session.stateless.RoomRateEntityControllerRemote;
import ejb.session.stateless.RoomTypeEntityControllerRemote;
import javax.ejb.EJB;

/**
 *
 * @author Lance
 */
public class Main {

    @EJB
    private static RoomTypeEntityControllerRemote roomTypeEntityController;

    @EJB
    private static RoomRateEntityControllerRemote roomRateEntityController;

    @EJB
    private static RoomEntityControllerRemote roomEntityController;

    @EJB
    private static ReservationEntityControllerRemote reservationEntityController;

    @EJB
    private static CustomerEntityControllerRemote customerEntityController;

    @EJB
    private static PartnerEntityControllerRemote partnerEntityController;

    @EJB
    private static EmployeeEntityControllerRemote employeeEntityController;
    
    public static void main(String[] args) {
        ReservationApp reservationApp = new ReservationApp(roomTypeEntityController,roomRateEntityController,reservationEntityController,customerEntityController);
        reservationApp.runApp();
    }
    
}
