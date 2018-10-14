/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.EmployeeEntityControllerLocal;
import entity.EmployeeEntity;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import util.enumeration.EmployeeAccessRightsEnum;
import util.exception.EmployeeNotFoundException;

/**
 *
 * @author Lance
 */
@Singleton
@LocalBean
@Startup
public class InitSessionBean 
{

    @EJB
    private EmployeeEntityControllerLocal employeeEntityController;
    
    public InitSessionBean() {
    }
    @PostConstruct
    public void postConstruct()
    {
        try
        {
            employeeEntityController.retrieveEmployeeByUsername("Admin");
        }
        catch(EmployeeNotFoundException ex)
        {
            initializeData();
        }
    }

    private void initializeData() 
    {
        employeeEntityController.createNewEmployee(new EmployeeEntity(null, "Admin", "Admin", "Admin","password",EmployeeAccessRightsEnum.SYSTEM_ADMINISTRATOR));
    }
}
