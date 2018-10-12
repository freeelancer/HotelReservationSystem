/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horsmanagementclient;

import ejb.session.stateless.EmployeeEntityControllerRemote;
import entity.EmployeeEntity;

/**
 *
 * @author Lance
 */
class ManagementModule {
    private EmployeeEntity currentEmployeeEntity;
    private EmployeeEntityControllerRemote employeeEntityController;

    public ManagementModule() {
    }

    public ManagementModule(EmployeeEntity currentEmployeeEntity, EmployeeEntityControllerRemote employeeEntityController) {
        this.currentEmployeeEntity = currentEmployeeEntity;
        this.employeeEntityController = employeeEntityController;
    }
    
}
