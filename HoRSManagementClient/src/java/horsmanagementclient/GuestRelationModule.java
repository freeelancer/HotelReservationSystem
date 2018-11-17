/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horsmanagementclient;

import ejb.session.stateless.CustomerEntityControllerRemote;
import ejb.session.stateless.ReservationEntityControllerRemote;
import ejb.session.stateless.RoomTypeEntityControllerRemote;
import entity.CustomerEntity;
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
import util.exception.CustomerNotFoundException;
import util.exception.RoomNotFoundException;
import util.exception.RoomTypeNotFoundException;

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
        System.out.println("Type the dates (dd/MM/yyyy) you wish to reserve.");
        System.out.println("Type in a date:");

        String response;
        Date date ;
        
        System.out.print("> ");
        response = scanner.nextLine();

        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(response); 
            while(!response.matches("\\d{2}/\\d{2}/\\d{4}") || !validateCheckIn(date)) {

                System.out.println("Invalid response! Please try again.");
                System.out.print("> ");
                response = scanner.nextLine();

                try {
                    date = new SimpleDateFormat("dd/MM/yyyy").parse(response); 
                } catch (Exception e){
                    System.out.println(e);
                }              
            }
            List<RoomTypeEntity> availableRoomTypes = roomTypeEntityController.searchRoomTypesByDate(date);
            System.out.println("Following rooms are available on " + response);            
            for(RoomTypeEntity roomType:availableRoomTypes){
                System.out.println(roomType.getName());
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }

    private void walkInReserveRoom() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*** Reservation System :: Search for Hotel Room ***\n");
        List<RoomTypeEntity> roomTypeList = roomTypeEntityController.retrieveAllRoomTypes();
        int numRoomType = roomTypeList.size();
        int count;
        Integer response = 0;
        
        while(true){
            count=1;
            while(count<=numRoomType){
                if(!roomTypeList.get(count-1).getDisable())
                {
                    System.out.println("" + count + ": " + roomTypeList.get(count-1).getName());
                }
                count++;
            }
            int lastOption = numRoomType+1;
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
       
        try {
            roomTypeToBook = roomTypeEntityController.retrieveRoomTypeByName(roomTypeToBook.getName());
        } catch (RoomTypeNotFoundException ex) {
        
        }
        
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
        
        System.out.println("Input Number Of Rooms> ");
        Integer numRooms = scanner.nextInt();
        scanner.nextLine();
        
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
            
            List<Date> datesUnavailable = roomTypeEntityController.checkAvailability(checkInDate, checkOutDate, roomTypeToBook, numRooms);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            
            if (datesUnavailable.isEmpty()){
                
                System.out.println("Room is available. Confirm reservation for:");
                System.out.println("Room Type: " + roomTypeToBook.getName());
                System.out.println("Check-in date: " + dateFormat.format(checkInDate));
                System.out.println("Check-out date: " + dateFormat.format(checkOutDate));
                BigDecimal totalAmount = reservationEntityController.calculateTotalAmount(roomTypeToBook.getName(), checkInDate, checkOutDate, numRooms);
                System.out.println("Total Amount: " + df.format(totalAmount));
                System.out.print("Type Enter to confirm. Type 'c' to cancel");
                System.out.print("> ");
                response = scanner.nextLine();
                System.out.println("-------------------");
                if (!response.equals("c")){ 
                    
                    String username, password;
                    
                    do 
                    {
                    System.out.print("Enter username> ");
                    username = scanner.nextLine().trim();
                    } while(username.length() == 0);

                    CustomerEntity currentCustomerEntity = new CustomerEntity();
                            
                    try {
                         currentCustomerEntity = customerEntityController.retrieveCustomerByUsername(username);
                    } catch (CustomerNotFoundException ex) {
                        System.out.println("--- User don't exist! Input details. ---\n");

                        CustomerEntity newCustomerEntity = new CustomerEntity();
                         
                        do 
                        {
                        System.out.print("Enter username> ");
                        username = scanner.nextLine().trim();
                        } while(username.length() == 0);

                        do
                        {
                        System.out.print("Enter password> ");
                        password = scanner.nextLine().trim();
                        } while(password.length() == 0);
                        
                        newCustomerEntity.setUsername(username);
                        newCustomerEntity.setPassword(password);

                        System.out.print("Enter first name> ");
                        newCustomerEntity.setFirstName(scanner.nextLine().trim());
                        System.out.print("Enter last name> ");
                        newCustomerEntity.setLastName(scanner.nextLine().trim());
                        System.out.print("Enter ID or Passport number> ");
                        newCustomerEntity.setIdNumber(scanner.nextLine().trim());
                        System.out.print("Enter contact number> ");
                        newCustomerEntity.setContactNumber(scanner.nextLine().trim());
                        System.out.print("Enter email> ");
                        newCustomerEntity.setEmail(scanner.nextLine().trim());
                        System.out.print("Enter address line 1> ");
                        newCustomerEntity.setAddressLine1(scanner.nextLine().trim());
                        System.out.print("Enter address line 2> ");
                        newCustomerEntity.setAddressLine2(scanner.nextLine().trim());
                        System.out.print("Enter postal code> ");
                        newCustomerEntity.setPostalCode(scanner.nextLine().trim());

                        currentCustomerEntity = customerEntityController.createNewCustomer(newCustomerEntity);
                        System.out.println("New account created successfully!: " + newCustomerEntity.getUsername());
                    } 

                    // check if booking is done after 2AM
                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.HOUR_OF_DAY, 2); 
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                    Date deadline = c.getTime(); 
                    Date now = new Date();
                    c.add(Calendar.DATE, 1);
                    Date endOfDay = c.getTime();
                            
                    ReservationEntity reservation = reservationEntityController.createNewReservation(currentCustomerEntity, roomTypeToBook, currentEmployeeEntity, null, checkInDate, checkOutDate);
                    if (now.after(deadline) && now.before(endOfDay)){
                        reservationEntityController.allocateRoomManually(reservation, roomTypeToBook);
                    }
                    
                    System.out.println("Reservation Successful! Reservation ID: " + reservation.getReservationId() + "\n");
                    return true;
                } else {
                    System.out.println("Reservation cancelled.");
                    return false;
                }
            } else {
                System.out.println("Room is unavailable on the following dates:");
                for(Date date:datesUnavailable){
                    System.out.println(dateFormat.format(date));
                }
            }
        }
    }
    
    private void searchRoom(RoomTypeEntity roomTypeToBook){
        
        RoomTypeEntity roomTypeEntity = new RoomTypeEntity();
        
        try {
            roomTypeEntity = roomTypeEntityController.retrieveRoomTypeByName(roomTypeToBook.getName());
        } catch (RoomTypeNotFoundException e) {
        
        }
        
        Scanner scanner = new Scanner(System.in);
       
        System.out.println("*** HoRS Reservation System :: Reserve Room ***\n");
        System.out.println("Room Type: " + roomTypeEntity.getName());
        System.out.println("Description: " + roomTypeEntity.getDescription());
        System.out.println("Amenities: " + roomTypeEntity.getAmenities());
        System.out.println("Room Size: " + roomTypeEntity.getSize() + " square meters");
        System.out.println("Bed Type: " + roomTypeEntity.getBedTypeEnum());
        System.out.println("Max Capacity: " + roomTypeEntity.getCapacity() + " pax");
        System.out.println("Normal Room Rate: " + roomTypeEntity.getRoomRateEntities().get(0).getRatePerNight());
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
            
            List<Date> datesUnavailable = roomTypeEntityController.checkAvailability(checkInDate, checkOutDate, roomTypeEntity);
        
            if (datesUnavailable.isEmpty()){
                System.out.println("Room is available. Confirm reservation for:");
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                System.out.println("Room Type: " + roomTypeEntity.getName());
                System.out.println("Check-in date: " + dateFormat.format(checkInDate));
                System.out.println("Check-out date: " + dateFormat.format(checkOutDate));
                BigDecimal totalAmount = reservationEntityController.calculateTotalAmount(roomTypeEntity.getName(), checkInDate, checkOutDate);
                DecimalFormat df = new DecimalFormat("$#,###.00");
                System.out.println("Total Amount: " + df.format(totalAmount));
                System.out.println("Enter to continue...");
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
        calendar.add(Calendar.DATE, -2);
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
        
        sc.nextLine();
        String response = sc.nextLine();
        String roomNumber = "";
        
        if(response.equals("c")){
            return;
        } else {
            try {
                List<String> roomsCheckedIn = reservationEntityController.checkInGuest(reservationId);
                System.out.println("Check-in successful! Room number/s: " + roomsCheckedIn.toString());
            } catch (RoomNotFoundException e) {
                e.printStackTrace();
            }
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
        
        sc.nextLine();
        String response = sc.nextLine();
        
        if(response.equals("c")){
            return;
        } else {
            reservationEntityController.checkOutGuest(reservationId);
            System.out.println("Check-out successful!");
        }
    }
    
}
