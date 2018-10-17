/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horsmanagementclient;

import ejb.session.stateless.CustomerEntityControllerRemote;
import ejb.session.stateless.ReservationEntityControllerRemote;
import entity.EmployeeEntity;

/**
 *
 * @author Lance
 */
class GuestRelationModule {
    private EmployeeEntity currentEmployeeEntity;
    private ReservationEntityControllerRemote ReservationEntityController;
    private CustomerEntityControllerRemote CustomerEntityController;

    public GuestRelationModule() {
    }

    public GuestRelationModule(EmployeeEntity currentEmployeeEntity, ReservationEntityControllerRemote ReservationEntityController, CustomerEntityControllerRemote CustomerEntityController) 
    {
        this.currentEmployeeEntity = currentEmployeeEntity;
        this.ReservationEntityController = ReservationEntityController;
        this.CustomerEntityController = CustomerEntityController;
    }
    
    void guestRelationOperations() 
    {
        
    }
    
}
