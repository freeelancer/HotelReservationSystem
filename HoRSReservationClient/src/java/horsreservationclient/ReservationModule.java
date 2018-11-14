/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horsreservationclient;

import ejb.session.stateless.CustomerEntityControllerRemote;
import ejb.session.stateless.ReservationEntityControllerRemote;
import ejb.session.stateless.RoomTypeEntityControllerRemote;
import entity.CustomerEntity;
import entity.ReservationEntity;
import entity.RoomTypeEntity;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import util.exception.ReservationNotFoundException;

/**
 *
 * @author Wai Kin
 */
public class ReservationModule {
    
    private CustomerEntity currentCustomerEntity;
    private CustomerEntityControllerRemote customerEntityController;
    private RoomTypeEntityControllerRemote roomTypeEntityController;
    private ReservationEntityControllerRemote reservationEntityController;
    

    public ReservationModule() {
    }

    public ReservationModule(CustomerEntity currentCustomerEntity, CustomerEntityControllerRemote customerEntityController, RoomTypeEntityControllerRemote roomTypeEntityController, ReservationEntityControllerRemote reservationEntityController) {
        this.currentCustomerEntity = currentCustomerEntity;
        this.customerEntityController = customerEntityController;
        this.roomTypeEntityController = roomTypeEntityController;
        this.reservationEntityController = reservationEntityController;
    }
   
    public void menuReservation() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true) {
            System.out.println("*** HoRS Reservation System :: Reservation System ***\n");
            System.out.println("1: Reserve Hotel Room");
            System.out.println("2: View My Reservation Details");
            System.out.println("3: View All My Reservations");
            System.out.println("4: Logout\n");
            response = 0;
            
            while(response < 1 || response > 4){
                System.out.print("> ");
                
                response = scanner.nextInt();
                scanner.nextLine();
                
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
            if (response == 4){
                return;
            }
        }
    }
    
    private void reserveHotelRoom(){
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*** HoRS Reservation System :: Reserve Room ***\n");
        
        List<RoomTypeEntity> roomTypeList = roomTypeEntityController.retrieveAllRoomTypes();
        int numRoomType = roomTypeList.size();
        int i;
        Integer response = 0;
        
        while(true){
            for (i = 1; i <= numRoomType; i++){
                System.out.println("" + i + ": " + roomTypeList.get(i-1).getName());
            }
            int lastOption = i;
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
            
            if(reserveRoom(roomTypeList.get(response-1))){
                break;
            }
            
        }
    }
    
    private boolean reserveRoom(RoomTypeEntity roomTypeToBook){
//        try {
//            roomTypeEntity = roomTypeEntityController.retrieveRoomTypeByName(roomTypeToBook.getName());
//        } catch (RoomTypeNotFoundException ex) {
//        
//        }
        
        Scanner scanner = new Scanner(System.in);
       
        System.out.println("*** HoRS Reservation System :: Reserve Room ***\n");
        System.out.println("Room Type: " + roomTypeToBook.getName());
        System.out.println("Description: " + roomTypeToBook.getDescription());
        System.out.println("Amenities: " + roomTypeToBook.getAmenities());
        System.out.println("Room Size: " + roomTypeToBook.getSize() + " square meters");
        System.out.println("Bed Type: " + roomTypeToBook.getBedTypeEnum());
        System.out.println("Max Capacity: " + roomTypeToBook.getCapacity() + " pax");
        DecimalFormat df = new DecimalFormat("$#,###.00");
        System.out.println("Normal Room Rate: " + df.format(roomTypeToBook.getRoomRateEntities().get(0).getRatePerNight()));
        System.out.println("-------------------");
        
        String response = "";
        Date checkInDate = new Date();
        Date checkOutDate = new Date();
        
        while (true){
            System.out.println("Type the dates (dd/MM/yyyy) you wish to reserve. Type 'b' to return.");
            System.out.println("Check in date:");
            System.out.print("> ");
            response = scanner.nextLine();
            if (response.equals("b")){
                return false;
            }
            try {
                checkInDate = new SimpleDateFormat("dd/MM/yyyy").parse(response); 
            } catch (Exception e){
                System.out.println(e);
            }
            
            while(!response.matches("\\d{2}/\\d{2}/\\d{4}") || !validateCheckIn(checkInDate)) {
                System.out.println("Invalid response! Please try again.");
                System.out.print("> ");
                response = scanner.nextLine();
                
                if (response.equals("b")){
                    return false;
                }   
                try {
                    checkInDate = new SimpleDateFormat("dd/MM/yyyy").parse(response); 
                } catch (Exception e){
                    System.out.println(e);
                }              
            }
            System.out.println("Check out date:");
            System.out.print("> ");
            response = scanner.nextLine();
            if (response.equals("b")){
                return false;
            }
            try {
                checkOutDate = new SimpleDateFormat("dd/MM/yyyy").parse(response); 
            } catch (Exception e){
                System.out.println(e);
            }  
 
            while(!response.matches("\\d{2}/\\d{2}/\\d{4}") || !validateCheckOut(checkInDate, checkOutDate)) {
                System.out.println("Invalid response! Please try again.");
                System.out.print("> ");
                response = scanner.nextLine();
                if (response.equals("b")){
                    return false;
                }
                try {
                    checkOutDate = new SimpleDateFormat("dd/MM/yyyy").parse(response); 
                } catch (Exception ex){
                    System.out.println(ex);
                }                
            } 
            
            List<Date> datesUnavailable = roomTypeEntityController.checkAvailability(checkInDate, checkOutDate);
            
            if (datesUnavailable.isEmpty()){
                
                System.out.println("Room is available. Confirm reservation for:");
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                System.out.println("Room Type: " + roomTypeToBook.getName());
                System.out.println("Check-in date: " + dateFormat.format(checkInDate));
                System.out.println("Check-out date: " + dateFormat.format(checkOutDate));
                BigDecimal totalAmount = reservationEntityController.calculateTotalAmount(roomTypeToBook.getName(), checkInDate, checkOutDate);
                System.out.println("Total Amount: " + df.format(totalAmount));
                System.out.print("Type Enter to confirm. Type 'c' to cancel");
                System.out.print("> ");
                response = scanner.nextLine();
                System.out.println("-------------------");
                if (!response.equals("c")){
                    reservationEntityController.createNewReservation(currentCustomerEntity, roomTypeToBook, null, null, checkInDate, checkOutDate);
                    System.out.println("Reservation Successful!");
                    return true;
                } else {
                    System.out.println("Reservation cancelled.");
                    return false;
                }
            }
        }
    }
    
    private void viewReservationDetails(){
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*** HoRS Reservation System :: View Reservation Details ***");
        System.out.println("Enter Reservation ID");
        System.out.print("> ");
        Long reservationId = scanner.nextLong();
        scanner.nextLine();
        
        ReservationEntity reservationEntity = new ReservationEntity();
        
        try {
            reservationEntity = reservationEntityController.retrieveReservationDetails(reservationId, currentCustomerEntity);
        } catch (ReservationNotFoundException ex) {
            System.out.println("Sorry, the reservation ID you entered is not found.");
            return;
        }
        
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Room Type: " + reservationEntity.getRoomTypeEntity().getName());
        System.out.println("Check-in Date: " + reservationEntity.getCheckInDate());
        System.out.println("Check-out Date: " + reservationEntity.getCheckOutDate());
        System.out.println("Total Amount: " + reservationEntity.getTotalAmount());
        
        System.out.println("Press Enter to continue: ");
        System.out.print("> ");
        scanner.nextLine();
    }
    
    private void viewAllReservations(){
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*** HoRS Reservation System :: View All Reservation ***\n");
        
        List<ReservationEntity> reservationList = new ArrayList<ReservationEntity>();
                
        try {
        reservationList = customerEntityController.retrieveAllReservations(currentCustomerEntity.getCustomerId());
        } catch (ReservationNotFoundException ex) {
            System.out.println(ex.getMessage());
        }        
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        for (ReservationEntity reservationEntity:reservationList){
            System.out.println("Reservation ID: " + reservationEntity.getReservationId());
            System.out.println("Room Type: " + reservationEntity.getRoomTypeEntity().getName());
            System.out.println("Check-in Date: " + dateFormat.format(reservationEntity.getCheckInDate()));
            System.out.println("Check-out Date: " + dateFormat.format(reservationEntity.getCheckOutDate()));
            System.out.println("-------------------");
        }
        
        System.out.println("Press Enter to continue: ");
        System.out.print("> ");
        scanner.nextLine();        
    }
    
    private boolean validateCheckIn(Date date){
        Date today = Calendar.getInstance().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DATE, -1);
        today = calendar.getTime();
        calendar.setTime(today);
        calendar.add(Calendar.YEAR, 1);
        Date latest = calendar.getTime();
        if (date.after(today) && date.before(latest)){
            return true;
        }
        return false;
    }
    
    private boolean validateCheckOut(Date checkInDate, Date checkOutDate){
        Date today = Calendar.getInstance().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DATE, -1);
        today = calendar.getTime();
        calendar.setTime(today);
        calendar.add(Calendar.YEAR, 1);
        Date latest = calendar.getTime();
        if (checkOutDate.after(checkInDate) && checkOutDate.after(today) && checkOutDate.before(latest)){
            return true;
        }
        return false;
    }
}
