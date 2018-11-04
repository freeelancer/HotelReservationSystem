/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horsreservationclient;

import ejb.session.stateless.CustomerEntityControllerRemote;
import entity.CustomerEntity;
import entity.RoomTypeEntity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Wai Kin
 */
public class ReservationModule {
    
    private CustomerEntity currentCustomerEntity;
    private CustomerEntityControllerRemote customerEntityController;

    public ReservationModule() {
    }

    public ReservationModule(CustomerEntity currentCustomerEntity, CustomerEntityControllerRemote customerEntityController) {
        this.currentCustomerEntity = currentCustomerEntity;
        this.customerEntityController = customerEntityController;
    }
   /*
    public void menuReservation() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true) {
            System.out.println("*** HoRS Reservation System :: Reservation System ***\n");
            System.out.println("1: Reserve Hotel Room");
            System.out.println("2: View My Reservation Details");
            System.out.println("3: View All My Reservations");
            System.out.println("4: Back\n");
            response = 0;
            
            while(response < 1 || response > 4){
                System.out.print("> ");
                
                response = scanner.nextInt();
                
                if (response == 1){
                    reserveHotelRoom();
                } else if (response == 2){
                    viewReservationDetails();
                } else if (response == 3){
                    viewAllReservations();
                } else if (response == 4){
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
                
                if (response == 4){
                    break;
                }
            }
        }
    }*/
    
    /*private void reserveHotelRoom(){
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*** HoRS Reservation System :: Reserve Room ***\n");
        
        List<RoomTypeEntity> roomTypeList = roomTypeEntityController.retrieveAllRoomTypes();
        int numRoomType = roomTypeList.size();
        int i;
        Integer response = 0;
        
        while(true){
            for (i = 1; i <= numRoomType; i++){
                System.out.println("" + i + ": " + roomTypeList.get(i-1));
                System.out.println("2: View My Reservation Details");
                System.out.println("3: View All My Reservations");
            }
            int lastOption = i+1;
            System.out.println("" + lastOption + ": Back\n");

            System.out.print("> ");
            response = scanner.nextInt();

            while (response < 1 || response > lastOption){
                System.out.println("Invalid response! Please try again.");
                System.out.print("> ");
                response = scanner.nextInt();
            }

            if (response == lastOption){
                break;
            }
            
            if(reserveRoom(roomTypeList.get(response-1))){
                break;
            }
            
        }
        return;
    }
    
    private boolean reserveRoom(RoomTypeEntity roomTypeEntity) throws Exception{
        
        Scanner scanner = new Scanner(System.in);
       
        System.out.println("*** HoRS Reservation System :: Reserve Room ***\n");
        System.out.println("Room Type: " + roomTypeEntity.getName());
        System.out.println("Description: " + roomTypeEntity.getDescription());
        System.out.println("Amenities: " + roomTypeEntity.getAmenities());
        System.out.println("Room Size: " + roomTypeEntity.getSize() + " square meters");
        System.out.println("Bed Type: " + roomTypeEntity.getBedTypeEnum());
        System.out.println("Max Capacity: " + roomTypeEntity.getCapacity() + " pax");
        System.out.println("-----------------------------------------------");
        
        while (true){
            System.out.println("Type the dates (dd/MM/yyyy) you wish to reserve. Type 'b' to return.");

            System.out.println("Check in date:");
            System.out.print("> ");
            String response = scanner.nextLine();

            while(!response.matches("\\d{2}/\\d{2}/\\d{4}")){
                if (response.equals("b")){return false;}
                System.out.println("Invalid response! Please try again.");
                System.out.print("> ");
                response = scanner.nextLine();
            }

            Date checkInDate = new SimpleDateFormat("dd/MM/yyyy").parse(response); 

            System.out.println("Check out date:");
            System.out.print("> ");
            response = scanner.nextLine();

            while(!response.matches("\\d{2}/\\d{2}/\\d{4}")){
                if (response.equals("b")){return false;}
                System.out.println("Invalid response! Please try again.");
                System.out.print("> ");
                response = scanner.nextLine();
            }

            Date checkOutDate = new SimpleDateFormat("dd/MM/yyyy").parse(response); 

            List<Date> datesUnavailable = roomTypeEntityController.checkAvailability(checkInDate, checkOutDate);
        
            if (datesUnavailable.size()==0){
                System.out.println("Room is available");
                
                reservationEntityController.createNewReservation(customerEntity, roomTypeEntity, null, null, checkInDate, checkOutDate);
            }
        }
    }*/
    
    private void viewReservationDetails(){
    
    }
    
    private void viewAllReservations(){
    
    }
    
    
}
