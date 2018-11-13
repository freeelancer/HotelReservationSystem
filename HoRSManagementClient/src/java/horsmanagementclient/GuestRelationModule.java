/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horsmanagementclient;

import ejb.session.stateless.CustomerEntityControllerRemote;
import ejb.session.stateless.ReservationEntityControllerRemote;
import ejb.session.stateless.RoomTypeEntityControllerRemote;
import entity.EmployeeEntity;
import entity.ReservationEntity;
import entity.RoomTypeEntity;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Lance
 */
class GuestRelationModule {
    private EmployeeEntity currentEmployeeEntity;
    private RoomTypeEntityControllerRemote roomTypeEntityController;
    private ReservationEntityControllerRemote reservationEntityController;
    private CustomerEntityControllerRemote customerEntityController;

    public GuestRelationModule() {
    }

    public GuestRelationModule(EmployeeEntity currentEmployeeEntity, ReservationEntityControllerRemote reservationEntityController, CustomerEntityControllerRemote customerEntityController, RoomTypeEntityControllerRemote roomTypeEntityController) 
    {
        this.currentEmployeeEntity = currentEmployeeEntity;
        this.reservationEntityController = reservationEntityController;
        this.customerEntityController = customerEntityController;
        this.roomTypeEntityController = roomTypeEntityController;
    }
    
    void guestRelationOperations() 
    {
        
        Scanner sc = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("1: Walk-in Search Room");
            System.out.println("2: Walk-in Reserve Room");
            System.out.println("3: Check-in Guest");
            System.out.println("4: Check-out Guest");
            System.out.println("5: Logout\n");
            response = 0;
            
            while(response < 1 || response > 5)
            {
                System.out.print("> ");

                response = sc.nextInt();
                sc.nextLine();
                if(response == 1)
                {
                    walkInSearchRoom();
                }
                else if(response == 2)
                {
                    walkInReserveRoom();
                }
                else if (response == 3)
                {
                    checkInGuest();
                }
                else if (response == 4)
                {
                    checkOutGuest();
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

    private void walkInSearchRoom() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*** Reservation System :: Search for Hotel Room ***\n");
        List<RoomTypeEntity> roomTypeList = roomTypeEntityController.retrieveAllRoomTypes();
        int numRoomType = roomTypeList.size();
        int i;
        Integer response = 0;
 
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
            return;
        }

        System.out.println("Press Enter to continue...");
        System.out.print("> ");
        scanner.nextLine();
        
        return;
    }

    private void walkInReserveRoom() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*** Reservation System :: Search for Hotel Room ***\n");
        List<RoomTypeEntity> roomTypeList = roomTypeEntityController.retrieveAllRoomTypes();
        int numRoomType = roomTypeList.size();
        int i;
        Integer response = 0;
 
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
            return;
        }

        reserveRoom(roomTypeList.get(response-1));
        
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
        
        String response = "";
        Date checkInDate = new Date();
        Date checkOutDate = new Date();
        
        while (true){
            System.out.println("Type the dates (dd/MM/yyyy) you wish to reserve. Type 'b' to return.");

            System.out.println("Check in date:");
            
            System.out.print("> ");
            response = scanner.nextLine();

            if (response.equals("b")){
                break;
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
                    break;
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
                break;
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
                    break;
                }
                
                try {
                    checkOutDate = new SimpleDateFormat("dd/MM/yyyy").parse(response); 
                } catch (Exception e){
                    System.out.println(e);
                }                
            } 
            
            List<Date> datesUnavailable = roomTypeEntityController.checkAvailability(checkInDate, checkOutDate);
        
            if (datesUnavailable.isEmpty()){
                System.out.println("Room is available. Confirm reservation for:");
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                System.out.println("Room Type: " + roomTypeEntity.getName());
                System.out.println("Check-in date: " + dateFormat.format(checkInDate));
                System.out.println("Check-out date: " + dateFormat.format(checkOutDate));
                BigDecimal totalAmount = reservationEntityController.calculateTotalAmount(roomTypeEntity.getName(), checkInDate, checkOutDate);
                DecimalFormat df = new DecimalFormat("#,###.00");
                System.out.println("Total Amount: " + df.format(totalAmount));
                System.out.println("Login to reserve. Press Enter to return to main page");
                System.out.print("> ");
                scanner.nextLine();
                return;
            } else {
                System.out.println("*** Following dates are unavailable ***");
                for (Date date:datesUnavailable){
                    System.out.println(date.toString());
                }
                System.out.println("-------------------");
                System.out.println("Select another room or another date! ");
            }
        }
    }
    
    private boolean validateCheckIn(Date date){
        Date today = Calendar.getInstance().getTime();
        Calendar calendar = Calendar.getInstance();
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
        calendar.add(Calendar.YEAR, 1);
        Date latest = calendar.getTime();
        if (checkOutDate.after(checkInDate) && checkOutDate.after(today) && checkOutDate.before(latest)){
            return true;
        }
        return false;
    }   
    

    private void checkInGuest() {
        System.out.println("*** HoRS Reservation System :: Check-In ***\n");
        
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Enter the reservation ID: ");
        System.out.print("> ");
        Long reservationId = sc.nextLong();
        
        ReservationEntity reservation = reservationEntityController.retrieveReservationById(reservationId);
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Room Type: " + reservation.getRoomTypeEntity().getName());
        System.out.println("Check-in date: " + dateFormat.format(reservation.getCheckInDate()));
        System.out.println("Check-out date: " + dateFormat.format(reservation.getCheckOutDate()));
        System.out.println("Total Amount: " + reservation.getTotalAmount().toString());
        System.out.println("Press Enter to continue with check-in or cancel by entering 'c'...");
        System.out.print("> ");
        
        String response = sc.nextLine();
        String roomNumber = "";
        
        if(response.equals("c")){
            return;
        } else {
            roomNumber = reservationEntityController.checkInGuest(reservationId);
            System.out.println("Check-in successful! Room number: " + roomNumber);
        }
    }

    private void checkOutGuest() {
        System.out.println("*** HoRS Reservation System :: Check-Out ***\n");
        
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Enter the reservation ID: ");
        System.out.print("> ");
        Long reservationId = sc.nextLong();
        
        ReservationEntity reservation = reservationEntityController.retrieveReservationById(reservationId);
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Room Type: " + reservation.getRoomTypeEntity().getName());
        System.out.println("Check-in date: " + dateFormat.format(reservation.getCheckInDate()));
        System.out.println("Check-out date: " + dateFormat.format(reservation.getCheckOutDate()));
        System.out.println("Total Amount: " + reservation.getTotalAmount().toString());
        System.out.println("Press Enter to continue with check-out or cancel by entering 'c'...");
        System.out.print("> ");
        
        String response = sc.nextLine();
        
        if(response.equals("c")){
            return;
        } else {
            reservationEntityController.checkOutGuest(reservationId);
            System.out.println("Check-out successful!");
        }
    }
    
}
