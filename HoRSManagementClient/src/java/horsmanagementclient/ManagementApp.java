/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horsmanagementclient;

import ejb.session.stateless.EmployeeEntityControllerRemote;
import entity.EmployeeEntity;
import java.util.Scanner;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author Lance
 */
class ManagementApp {
    private EmployeeEntityControllerRemote employeeEntityController;
    private EmployeeEntity currentEmployeeEntity;
    private ManagementModule managementModule;
    
    public ManagementApp() {
    }

    public ManagementApp(EmployeeEntityControllerRemote employeeEntityController) {
        this.employeeEntityController = employeeEntityController;
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

                if(response == 1)
                {
                    try
                    {
                        doLogin();
                        System.out.println("Login successful!\n");    
                        managementModule = new ManagementModule(currentEmployeeEntity,employeeEntityController);
                        managementModule.menuEmployeeOperations();
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
        }
        else
        {
            throw new InvalidLoginCredentialException("Missing login credential!");
        }
    }
    
}
