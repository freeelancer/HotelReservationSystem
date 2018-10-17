/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horsmanagementclient;

import ejb.session.stateless.RoomRateEntityControllerRemote;
import entity.EmployeeEntity;

/**
 *
 * @author Lance
 */
class SalesManagerModule {
    private EmployeeEntity currentEmployeeEntity;
    private RoomRateEntityControllerRemote RoomRateEntityController;

    public SalesManagerModule() 
    {
    }

    public SalesManagerModule(EmployeeEntity currentEmployeeEntity, RoomRateEntityControllerRemote RoomRateEntityController) 
    {
        this.currentEmployeeEntity = currentEmployeeEntity;
        this.RoomRateEntityController = RoomRateEntityController;
    }

    void salesManagerOperations() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
