/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horsreservationclient;

import ejb.session.stateless.CustomerEntityControllerRemote;
import ejb.session.stateless.EmployeeEntityControllerRemote;
import ejb.session.stateless.PartnerEntityControllerRemote;
import ejb.session.stateless.ReservationEntityControllerRemote;
import ejb.session.stateless.RoomEntityControllerRemote;
import ejb.session.stateless.RoomRateEntityControllerRemote;
import ejb.session.stateless.RoomTypeEntityControllerRemote;
import entity.CustomerEntity;
import entity.EmployeeEntity;
import entity.RoomTypeEntity;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import util.exception.CustomerNotFoundException;
import util.exception.UsernameExistException;
import util.exception.InvalidLoginCredentialException;
import util.exception.RoomExistException;

/**
 *
 * @author Wai Kin
 */
public class ReservationApp {
    private CustomerEntityControllerRemote customerEntityController;
    private CustomerEntity currentCustomerEntity;
    private EmployeeEntityControllerRemote employeeEntityController;
    private EmployeeEntity currentEmployeeEntity;
    private PartnerEntityControllerRemote partnerEntityController;
    private RoomTypeEntityControllerRemote roomTypeEntityController;
    private RoomRateEntityControllerRemote roomRateEntityController;
    private RoomEntityControllerRemote roomEntityController;
    private ReservationEntityControllerRemote reservationEntityController;
    private ReservationModule reservationModule;

    public ReservationApp() {
    }

    public ReservationApp(RoomTypeEntityControllerRemote roomTypeEntityController, RoomRateEntityControllerRemote roomRateEntityController, ReservationEntityControllerRemote reservationEntityController, CustomerEntityControllerRemote customerEntityController) {
        this.roomTypeEntityController = roomTypeEntityController;
        this.roomRateEntityController = roomRateEntityController;
        this.reservationEntityController = reservationEntityController;
        this.customerEntityController = customerEntityController;
    }
    
     public void runApp()
    {
        Scanner sc = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** Welcome to HoRS Reservation System - Visitor Page ***\n");
            System.out.println("1: Login as Guest");
            System.out.println("2: Register as Guest");
            System.out.println("3: Search Hotel Room");
            System.out.println("4: Exit\n");
            response = 0;
            
            while(response < 1 || response > 4)
            {
                System.out.print("> ");

                response = sc.nextInt();

                if(response == 1)
                {
                    try
                    {
                        doLogin();
                        System.out.println("Login successful!\n");    
                        reservationModule = new ReservationModule(currentCustomerEntity, roomTypeEntityController, reservationEntityController);
                        reservationModule.menuReservation();
                    }
                    catch(InvalidLoginCredentialException ex) 
                    {
                        System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                    }
                }
                else if (response == 2)
                {
                    try
                    {
                        doRegistration();
                    }
                    catch(UsernameExistException ex)
                    {
                        System.out.println("Account already exist: " + ex.getMessage() + "\n");
                    }
                }
                else if (response == 3)
                {
                    try
                    {
                        doSearchHotelRoom();
                    }
                    catch(RoomExistException ex)
                    {
                        System.out.println("There are no rooms available at the moment.\n");
                    }
                }
                else if (response == 4)
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
     
    public void doLogin() throws InvalidLoginCredentialException
    {
        Scanner sc = new Scanner(System.in);
        String username = "";
        String password = "";
        
        System.out.println("*** Reservation System :: Login as Guest ***\n");
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
    
    public void doRegistration() throws UsernameExistException
    {
        Scanner sc = new Scanner(System.in);
        String username = "";
        String password = "";
        
        System.out.println("*** Reservation System :: Guest Registration ***\n");
        
        do 
        {
        System.out.print("Enter username> ");
        username = sc.nextLine().trim();
        } while(username.length() == 0);
        
        do
        {
        System.out.print("Enter password> ");
        password = sc.nextLine().trim();
        } while(password.length() == 0);
        
        try
        {
            currentCustomerEntity = customerEntityController.retrieveCustomerByUsername(username);
            
            throw new UsernameExistException("Customer username " + username + " does already exist!");
        }
        catch (CustomerNotFoundException ex)
        {
            System.out.println("*** Reservation System :: Registration Details ***\n");
            
            CustomerEntity newCustomerEntity = new CustomerEntity();
            
            newCustomerEntity.setUsername(username);
            newCustomerEntity.setPassword(password);
            
            System.out.print("Enter first name> ");
            newCustomerEntity.setFirstName(sc.nextLine().trim());
            System.out.print("Enter last name> ");
            newCustomerEntity.setLastName(sc.nextLine().trim());
            System.out.print("Enter ID or Passport number> ");
            newCustomerEntity.setIdNumber(sc.nextLine().trim());
            System.out.print("Enter contact number> ");
            newCustomerEntity.setContactNumber(sc.nextLine().trim());
            System.out.print("Enter email> ");
            newCustomerEntity.setEmail(sc.nextLine().trim());
            System.out.print("Enter address line 1> ");
            newCustomerEntity.setAddressLine1(sc.nextLine().trim());
            System.out.print("Enter address line 2> ");
            newCustomerEntity.setAddressLine2(sc.nextLine().trim());
            System.out.print("Enter postal code> ");
            newCustomerEntity.setPostalCode(sc.nextLine().trim());
            
            newCustomerEntity = customerEntityController.createNewCustomer(newCustomerEntity);
            System.out.println("New account created successfully!: " + newCustomerEntity.getUsername() + "\n");
        } 
    }
    
    public void doSearchHotelRoom() throws RoomExistException
    {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*** Reservation System :: Search for Hotel Room ***\n");
        List<RoomTypeEntity> roomTypeList = roomTypeEntityController.retrieveAllRoomTypes();
        int numRoomType = roomTypeList.size();
        int i;
        Integer response = 0;
        
        while(true){
            for (i = 1; i <= numRoomType; i++){
                System.out.println("" + i + ": " + roomTypeList.get(i-1));
            }
            int lastOption = i+1;
            System.out.println("" + lastOption + ": Back\n");

            System.out.print("> ");
            response = scanner.nextInt();
            scanner.nextLine();

            while (response < 1 || response > lastOption){
                System.out.println("Invalid response! Please try again.");
                System.out.print("> ");
                response = scanner.nextInt();
                scanner.nextLine();
            }

            if (response == lastOption){
                break;
            }
            
            reserveRoom(roomTypeList.get(response-1));
            
        }
        return;
    }
    
    private void reserveRoom(RoomTypeEntity roomTypeEntity){
        
        Scanner scanner = new Scanner(System.in);
       
        System.out.println("*** HoRS Reservation System :: Reserve Room ***\n");
        System.out.println("Room Type: " + roomTypeEntity.getName());
        System.out.println("Description: " + roomTypeEntity.getDescription());
        System.out.println("Amenities: " + roomTypeEntity.getAmenities());
        System.out.println("Room Size: " + roomTypeEntity.getSize() + " square meters");
        System.out.println("Bed Type: " + roomTypeEntity.getBedTypeEnum());
        System.out.println("Max Capacity: " + roomTypeEntity.getCapacity() + " pax");
        System.out.println("-------------------");
        
        while (true){
            System.out.println("Type the dates (dd/MM/yyyy) you wish to reserve. Type 'b' to return.");

            System.out.println("Check in date:");
            System.out.print("> ");
            String response = scanner.nextLine();

            while(!response.matches("\\d{2}/\\d{2}/\\d{4}")){
                if (response.equals("b")){return;}
                System.out.println("Invalid response! Please try again.");
                System.out.print("> ");
                response = scanner.nextLine();
            }
            
            Date checkInDate = new Date();
            
            try {
                checkInDate = new SimpleDateFormat("dd/MM/yyyy").parse(response); 
            } catch (Exception e){
                System.out.println(e);
            }
            
            System.out.println("Check out date:");
            System.out.print("> ");
            response = scanner.nextLine();

            while(!response.matches("\\d{2}/\\d{2}/\\d{4}")){
                if (response.equals("b")){return;}
                System.out.println("Invalid response! Please try again.");
                System.out.print("> ");
                response = scanner.nextLine();
            }
               
            Date checkOutDate = new Date();
            
            try {
                checkOutDate = new SimpleDateFormat("dd/MM/yyyy").parse(response); 
            } catch (Exception e){
                System.out.println(e);
            }
            
            List<Date> datesUnavailable = roomTypeEntityController.checkAvailability(checkInDate, checkOutDate);
        
            if (datesUnavailable.isEmpty()){
                System.out.println("Room is available:");
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                System.out.println("Room Type: " + roomTypeEntity.getName());
                System.out.println("Check-in date: " + dateFormat.format(checkInDate));
                System.out.println("Check-out date: " + dateFormat.format(checkOutDate));
                System.out.println("--- Login to reserve ---");
                System.out.print("1. Login as Guest");
                System.out.print("2. Register as Guest");
                System.out.print("3. Cancel");
                System.out.println("> ");
                Integer response2 = scanner.nextInt();
                scanner.nextLine();
                
                while (response2 < 1 || response2 > 3){
                    System.out.println("Invalid response! Please try again.");
                    System.out.print("> ");
                    response2 = scanner.nextInt();
                    scanner.nextLine();
                }
                
                if (response2 == 1){
                    try{
                        doLogin();
                    } catch (InvalidLoginCredentialException ex){
                        System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                    }
                } else if (response2 == 2){
                    try{
                        doRegistration();
                    } catch(UsernameExistException ex){
                        System.out.println("Username already exist: " + ex.getMessage() + "\n");
                    }
                } else if (response2 == 3){
                    return;
                }
            }
        }
    }
}
