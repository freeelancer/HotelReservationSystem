/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horsmanagementclient;

import ejb.session.stateless.EmployeeEntityControllerRemote;
import javax.ejb.EJB;

/**
 *
 * @author Lance
 */
public class Main {

    @EJB
    private static EmployeeEntityControllerRemote employeeEntityController;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ManagementApp managementApp=new ManagementApp(employeeEntityController);
        managementApp.runApp();
    }
    
}
