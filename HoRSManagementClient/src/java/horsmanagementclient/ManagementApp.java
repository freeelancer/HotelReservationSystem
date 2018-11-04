/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horsmanagementclient;

import ejb.session.stateless.CustomerEntityControllerRemote;
import ejb.session.stateless.EmployeeEntityControllerRemote;
import ejb.session.stateless.PartnerEntityControllerRemote;
import ejb.session.stateless.ReservationEntityControllerRemote;
import ejb.session.stateless.RoomEntityControllerRemote;
import ejb.session.stateless.RoomRateEntityControllerRemote;
import ejb.session.stateless.RoomTypeEntityControllerRemote;
import entity.EmployeeEntity;
import java.util.Scanner;
import util.enumeration.EmployeeAccessRightsEnum;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author Lance
 */
class ManagementApp {
    private EmployeeEntityControllerRemote employeeEntityController;
    private EmployeeEntity currentEmployeeEntity;
    private PartnerEntityControllerRemote partnerEntityController;
    private RoomTypeEntityControllerRemote roomTypeEntityController;
    private RoomRateEntityControllerRemote roomRateEntityController;
    private RoomEntityControllerRemote roomEntityController;
    private ReservationEntityControllerRemote reservationEntityController;
    private CustomerEntityControllerRemote customerEntityController;
    private SystemAdminModule systemAdminModule;
    private OperationManagerModule operationManagerModule;
    private SalesManagerModule salesManagerModule;
    private GuestRelationModule guestRelationModule;
            
    public ManagementApp() {
    }

    public ManagementApp(EmployeeEntityControllerRemote employeeEntityController, PartnerEntityControllerRemote partnerEntityController, RoomTypeEntityControllerRemote roomTypeEntityController, RoomRateEntityControllerRemote roomRateEntityController, RoomEntityControllerRemote roomEntityController, ReservationEntityControllerRemote reservationEntityController, CustomerEntityControllerRemote customerEntityController) {
        this.employeeEntityController = employeeEntityController;
        this.partnerEntityController = partnerEntityController;
        this.roomTypeEntityController = roomTypeEntityController;
        this.roomRateEntityController = roomRateEntityController;
        this.roomEntityController = roomEntityController;
        this.reservationEntityController = reservationEntityController;
        this.customerEntityController = customerEntityController;
    }


    public void runApp()
    {
        Scanner sc = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** Welcome to HoRS Management Client ***\n");
            System.out.println("1: Login");
            System.out.println("2: Exit\n");
            response = 0;
            
            while(response < 1 || response > 2)
            {
                System.out.print("> ");

                response = sc.nextInt();
                sc.nextLine();

                if(response == 1)
                {
                    try
                    {
                        doLogin();
                        //System.out.println("Login successful!\n");    
                        //menuEmployeeOperations();
                    }
                    catch(InvalidLoginCredentialException ex) 
                    {
                        System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                    }
                }
                else if (response == 2)
                {
                    break;
                }
                else
                {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 2)
            {
                break;
            }
        }
    }
    public void doLogin() throws InvalidLoginCredentialException
    {
        Scanner sc = new Scanner(System.in);
        String username = "";
        String password = "";
        
        System.out.println("*** Management Client System :: Login ***\n");
        System.out.print("Enter username> ");
        username = sc.nextLine().trim();
        System.out.print("Enter password> ");
        password = sc.nextLine().trim();
        
        if(username.length() > 0 && password.length() > 0)
        {
            currentEmployeeEntity = employeeEntityController.employeeLogin(username, password);
            menuEmployeeOperations();
        }
        else
        {
            throw new InvalidLoginCredentialException("Missing login credential!");
        }
    }
    private void menuEmployeeOperations() 
    {
        System.out.println("*** Management Client System ***\n");
        System.out.println("You are login as " + currentEmployeeEntity.getFirstName() + " " + currentEmployeeEntity.getLastName() + " with " + currentEmployeeEntity.getAccessRightsEnum().toString() + " rights\n");
        EmployeeAccessRightsEnum currAccessRights = currentEmployeeEntity.getAccessRightsEnum();
        if(currAccessRights.equals(EmployeeAccessRightsEnum.SYSTEM_ADMINISTRATOR))
        {
            systemAdminModule = new SystemAdminModule(currentEmployeeEntity,employeeEntityController,partnerEntityController);
            systemAdminModule.systemAdminOperations();
        }
        else if(currAccessRights.equals(EmployeeAccessRightsEnum.OPERATION_MANAGER))
        {
            operationManagerModule = new OperationManagerModule(currentEmployeeEntity,roomTypeEntityController,roomEntityController);
            operationManagerModule.operationModuleOperations();
        }
        else if(currAccessRights.equals(EmployeeAccessRightsEnum.SALES_MANAGER))
        {
            salesManagerModule = new SalesManagerModule(currentEmployeeEntity,roomRateEntityController);
            salesManagerModule.salesManagerOperations();
        }
        else if(currAccessRights.equals(EmployeeAccessRightsEnum.GUEST_RELATION_OFFICER))
        {
            guestRelationModule = new GuestRelationModule(currentEmployeeEntity,reservationEntityController,customerEntityController);
            guestRelationModule.guestRelationOperations();
        }
    }  
    
}
