/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horsmanagementclient;

import ejb.session.stateless.EmployeeEntityControllerRemote;
import entity.EmployeeEntity;
import java.util.Scanner;
import util.enumeration.EmployeeAccessRightsEnum;

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

    void menuEmployeeOperations() 
    {
        System.out.println("*** Management Client System ***\n");
        System.out.println("You are login as " + currentEmployeeEntity.getFirstName() + " " + currentEmployeeEntity.getLastName() + " with " + currentEmployeeEntity.getAccessRightEnum().toString() + " rights\n");
        EmployeeAccessRightsEnum currAccessRights = currentEmployeeEntity.getAccessRightEnum();
        if(currAccessRights.equals(EmployeeAccessRightsEnum.SYSTEM_ADMINISTRATOR))
        {
            systemAdminOperations();
        }
        else if(currAccessRights.equals(EmployeeAccessRightsEnum.OPEARTION_MANAGER))
        {
            generalOperations();
        }
        else if(currAccessRights.equals(EmployeeAccessRightsEnum.SALES_MANAGER))
        {
            salesOperations();
        }
        else if(currAccessRights.equals(EmployeeAccessRightsEnum.GUEST_RELATION_OFFICER))
        {
            guestRelationOperations();
        }
    }  

    private void systemAdminOperations() 
    {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("1: Create New Employee");
            System.out.println("2: Create New Partner");
            System.out.println("3: View All Existing Employees");
            System.out.println("4: View All Existing Partners");
            System.out.println("5: Logout\n");
            response = 0;
            
            while(response < 1 || response > 5)
            {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1)
                {
                    createEmployeeOperation();
                }
                else if(response == 2)
                {
                    createPartnerOperation();
                }
                else if (response == 3)
                {
                    viewAllEmployeeOperation();
                }
                else if (response == 4)
                {
                    viewAllPartnerOperation();
                }
                else if (response == 5)
                {
                    break;
                }
                else
                {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 4)
            {
                break;
            }
        }
    }
    
    private void createEmployeeOperation() 
    {
        Scanner sc=new Scanner(System.in);
        EmployeeEntity newEmployeeEntity = new EmployeeEntity();
        System.out.println("*** System Administrator Operations :: Create New Customer ***\n");
        System.out.print("Enter First Name> ");
        newEmployeeEntity.setFirstName(sc.nextLine().trim());
        System.out.print("Enter Last Name> ");
        newEmployeeEntity.setLastName(sc.nextLine().trim());
        System.out.print("Enter Username> ");
        newEmployeeEntity.setUsername(sc.nextLine().trim());
        System.out.print("Enter Password> ");
        newEmployeeEntity.setPassword(sc.nextLine().trim());
        System.out.print("Enter Access Rights Of New Employee\n'1' for System Administrator\n'2' For Operations Manager\n'3' For Sales Manager\n'4' For Guest Relation Officer\n>");
        newEmployeeEntity.setAccessRightEnum(EmployeeAccessRightsEnum.values()[sc.nextInt()-1]);
        newEmployeeEntity = employeeEntityController.createNewEmployee(newEmployeeEntity);
        System.out.println("New Employee Created Successfully!: " + newEmployeeEntity.getFirstName() + " " + newEmployeeEntity.getLastName() + "\n");    
    }
        
    private void createPartnerOperation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void viewAllEmployeeOperation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void viewAllPartnerOperation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void generalOperations() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void salesOperations() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void guestRelationOperations() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    
            
}
