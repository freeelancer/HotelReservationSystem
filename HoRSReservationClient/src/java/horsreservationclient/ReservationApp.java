/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horsreservationclient;

import ejb.session.stateless.CustomerEntityControllerRemote;
import entity.CustomerEntity;
import java.util.Scanner;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author Wai Kin
 */
public class ReservationApp {
    private CustomerEntityControllerRemote customerEntityController;
    private CustomerEntity currentCustomerEntity;
    private ReservationModule reservationModule;

    public ReservationApp() {
    }

    public ReservationApp(CustomerEntityControllerRemote customerEntityController) {
        this.customerEntityController = customerEntityController;
    }
    
     public void runApp()
    {
        Scanner sc = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** Welcome to HoRS Reservation System ***\n");
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
                        reservationModule = new ReservationModule(currentCustomerEntity,customerEntityController);
                        //Run reservationModule operations
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
        
        System.out.println("*** Reservation System :: Login ***\n");
        System.out.print("Enter username> ");
        username = sc.nextLine().trim();
        System.out.print("Enter password> ");
        password = sc.nextLine().trim();
        
        if(username.length() > 0 && password.length() > 0)
        {
            currentCustomerEntity = customerEntityController.customerLogin(username, password);
        }
        else
        {
            throw new InvalidLoginCredentialException("Missing login credential!");
        }
    }
    
}
