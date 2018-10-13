/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horsreservationclient;

import ejb.session.stateless.CustomerEntityControllerRemote;
import javax.ejb.EJB;

/**
 *
 * @author Lance
 */
public class Main {

    @EJB 
    private static CustomerEntityControllerRemote customerEntityController;
    
    public static void main(String[] args) {
        ReservationApp reservationApp = new ReservationApp(customerEntityController);
        reservationApp.runApp();
    }
    
}
