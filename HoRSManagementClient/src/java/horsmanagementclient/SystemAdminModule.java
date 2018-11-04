/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horsmanagementclient;

import ejb.session.stateless.EmployeeEntityControllerRemote;
import ejb.session.stateless.PartnerEntityControllerRemote;
import entity.EmployeeEntity;
import entity.PartnerEntity;
import java.util.List;
import java.util.Scanner;
import util.enumeration.EmployeeAccessRightsEnum;
import util.enumeration.PartnerAccessRightsEnum;
/**
 *
 * @author Lance
 */
class SystemAdminModule {
    private EmployeeEntity currentEmployeeEntity;
    private EmployeeEntityControllerRemote employeeEntityController;
    private PartnerEntityControllerRemote partnerEntityController;
    public SystemAdminModule() {
    }

    public SystemAdminModule(EmployeeEntity currentEmployeeEntity, EmployeeEntityControllerRemote employeeEntityController, PartnerEntityControllerRemote partnerEntityController) {
        this.currentEmployeeEntity = currentEmployeeEntity;
        this.employeeEntityController = employeeEntityController;
        this.partnerEntityController = partnerEntityController;
    }

    void systemAdminOperations() 
    {
        Scanner sc = new Scanner(System.in);
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

                response = sc.nextInt();
                sc.nextLine();
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
            
            if(response == 5)
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
        newEmployeeEntity.setAccessRightsEnum(EmployeeAccessRightsEnum.values()[sc.nextInt()-1]);
        newEmployeeEntity = employeeEntityController.createNewEmployee(newEmployeeEntity);
        System.out.println("New Employee Created Successfully!: " + newEmployeeEntity.getFirstName() + " " + newEmployeeEntity.getLastName() + "\n");    
    }
        
    private void createPartnerOperation() 
    {
        Scanner sc=new Scanner(System.in);
        PartnerEntity newPartnerEntity = new PartnerEntity();
        System.out.println("*** System Administrator Operations :: Create New Customer ***\n");
        System.out.print("Enter First Name> ");
        newPartnerEntity.setFirstName(sc.nextLine().trim());
        System.out.print("Enter Last Name> ");
        newPartnerEntity.setLastName(sc.nextLine().trim());
        System.out.print("Enter Username> ");
        newPartnerEntity.setUsername(sc.nextLine().trim());
        System.out.print("Enter Password> ");
        newPartnerEntity.setPassword(sc.nextLine().trim());
        System.out.print("Enter Access Rights Of New Employee\n'1' For Employee\n'2' For Manager\n>");
        newPartnerEntity.setAccessRightsEnum(PartnerAccessRightsEnum.values()[sc.nextInt()-1]);
        newPartnerEntity = partnerEntityController.createNewPartner(newPartnerEntity);
        System.out.println("New Partner Created Successfully!: " + newPartnerEntity.getFirstName() + " " + newPartnerEntity.getLastName() + "\n");        
    }

    private void viewAllEmployeeOperation() 
    {
        Scanner sc = new Scanner(System.in);
        List<EmployeeEntity> employeeEntitys = employeeEntityController.retrieveAllEmployees();
        
        System.out.println("*** Management Client System :: View All Exisiting Employees ***\n");
        for(EmployeeEntity tempEmployee:employeeEntitys)
        {
            System.out.println("Employee ID: "+tempEmployee.getEmployeeId());
            System.out.println("Employee Name: " + tempEmployee.getFirstName() + " " + tempEmployee.getLastName());
            System.out.println("Employee Username: " + tempEmployee.getUsername());
            System.out.println("Employee Access Rights: " + tempEmployee.getAccessRightsEnum().toString());
            System.out.println("------------------------");
        }
        System.out.print("Press Enter to continue...> ");
        sc.nextLine();
    }

    private void viewAllPartnerOperation() 
    {
        Scanner sc = new Scanner(System.in);
        List<PartnerEntity> partnerEntitys = partnerEntityController.retrieveAllPartners();
        System.out.println("*** Management Client System :: View All Exisiting Partners ***\n");
        for(PartnerEntity tempPartner:partnerEntitys)
        {
            System.out.println("Partner ID: "+tempPartner.getPartnerId());
            System.out.println("Partner Name: " + tempPartner.getFirstName() + " " + tempPartner.getLastName());
            System.out.println("Partner Username: " + tempPartner.getUsername());
            System.out.println("Partner Access Rights: " + tempPartner.getAccessRightsEnum().toString());
            System.out.println("------------------------");
        }
        System.out.print("Press any key to continue...> ");
        sc.nextLine();
    }      
}
