/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horsmanagementclient;

import ejb.session.stateless.RoomEntityControllerRemote;
import ejb.session.stateless.RoomTypeEntityControllerRemote;
import entity.EmployeeEntity;

/**
 *
 * @author Lance
 */
class OperationManagerModule {
    private EmployeeEntity currentEmployeeEntity;
    private RoomTypeEntityControllerRemote RoomTypeEntityController;
    private RoomEntityControllerRemote RoomEntityController;

    public OperationManagerModule() {
    }

    public OperationManagerModule(EmployeeEntity currentEmployeeEntity, RoomTypeEntityControllerRemote RoomTypeEntityController, RoomEntityControllerRemote RoomEntityController) 
    {
        this.currentEmployeeEntity = currentEmployeeEntity;
        this.RoomTypeEntityController = RoomTypeEntityController;
        this.RoomEntityController = RoomEntityController;
    }

    void operationModuleOperations() 
    {
        
    }
    
}
