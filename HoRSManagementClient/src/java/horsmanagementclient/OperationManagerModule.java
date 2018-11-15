/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horsmanagementclient;

import ejb.session.stateless.EjbTimerControllerRemote;
import ejb.session.stateless.ReportEntityControllerRemote;
import ejb.session.stateless.RoomEntityControllerRemote;
import ejb.session.stateless.RoomTypeEntityControllerRemote;
import entity.AllocationExceptionEntity;
import entity.EmployeeEntity;
import entity.RoomEntity;
import entity.RoomExceptionReportEntity;
import entity.RoomTypeEntity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import util.enumeration.BedTypeEnum;
import util.exception.RoomAlreadyDisabledException;
import util.exception.RoomIsUsedException;
import util.exception.RoomNotFoundException;
import util.exception.RoomTypeAlreadyDisabledException;
import util.exception.RoomTypeNotFoundException;
import util.exception.RoomTypeStillUsedException;

/**
 *
 * @author Lance
 */
class OperationManagerModule {
    private EmployeeEntity currentEmployeeEntity;
    private RoomTypeEntityControllerRemote roomTypeEntityController;
    private RoomEntityControllerRemote roomEntityController;
    private ReportEntityControllerRemote reportEntityController;
    private EjbTimerControllerRemote timerController;

    public OperationManagerModule() {
    }

    public OperationManagerModule(EmployeeEntity currentEmployeeEntity, RoomTypeEntityControllerRemote roomTypeEntityController, RoomEntityControllerRemote roomEntityController, EjbTimerControllerRemote timerController) 
    {
        this.currentEmployeeEntity = currentEmployeeEntity;
        this.roomTypeEntityController = roomTypeEntityController;
        this.roomEntityController = roomEntityController;
        this.timerController = timerController;
    }

    void operationModuleOperations() 
    {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("1: Create New Room Type");
            System.out.println("2: View Room Type Details");
            System.out.println("3: Update Room Type");
            System.out.println("4: Delete Room Type");
            System.out.println("5: View All Room Types");
            System.out.println("6: Create New Room");
            System.out.println("7: Update Room");
            System.out.println("8: Delete Room");
            System.out.println("9: View All Rooms");
            System.out.println("10: View Room Allocation Exception Report");
            System.out.println("11: Allocate Rooms for Current Day");
            System.out.println("12: Logout\n");
            response = 0;
            
            while(response < 1 || response > 12)
            {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1)
                {
                    createNewRoomTypeOperation();
                }
                else if(response == 2)
                {
                    viewRoomTypeDetailsOperation();
                }
                else if (response == 3)
                {
                    updateRoomTypeOperations();
                }
                else if (response == 4)
                {
                    deleteRoomTypeOperation();
                }
                else if (response == 5)
                {
                    viewAllRoomTypesOperation();
                }
                else if (response == 6)
                {
                    createNewRoomOperation();
                }
                else if (response == 7)
                {
                    updateRoomOperation();
                }
                else if (response == 8)
                {
                    deleteRoomOperation();
                }
                else if (response == 9)
                {
                    viewAllRoomsOperation();
                }
                else if (response == 10)
                {
                    viewRoomAllocationExceptionResultOperation();
                }
                else if (response == 11)
                {
                    timerController.allocateRoom();
                    System.out.println("Rooms Allocated!\n");
                }
                else if (response == 12)
                {
                    break;
                }
                else
                {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 12)
            {
                break;
            }
        }
    }

    private void createNewRoomTypeOperation() 
    {
        Scanner sc = new Scanner(System.in);
        RoomTypeEntity newRoomTypeEntity = new RoomTypeEntity();
        System.out.println("*** Operation Manager Operations :: Create New Room Type ***\n");
        System.out.print("Enter Name Of Room Type> ");
        newRoomTypeEntity.setName(sc.nextLine().trim());
        System.out.print("Enter Description> ");
        newRoomTypeEntity.setDescription(sc.nextLine().trim());
        System.out.print("Enter Size Of Room Type> ");
        newRoomTypeEntity.setSize(sc.nextInt());
        sc.nextLine();
        System.out.print("Enter Type Of Bed\n1: Single\n2: Queen\n3: King\n4: Jumbo\n>");
        newRoomTypeEntity.setBedTypeEnum(BedTypeEnum.values()[sc.nextInt()-1]);
        System.out.print("Enter Capacity Of Room Type> ");
        newRoomTypeEntity.setCapacity(sc.nextInt());
        sc.nextLine();
        System.out.print("Enter Amenities Of Room Type with each amenity separated by a comma> ");
        newRoomTypeEntity.setAmenities(sc.nextLine().trim());
        newRoomTypeEntity.setDisable(Boolean.FALSE);
        newRoomTypeEntity.setUsed(Boolean.TRUE);
        newRoomTypeEntity = roomTypeEntityController.createNewRoomType(newRoomTypeEntity);
        System.out.println("Room Type "+newRoomTypeEntity.getName()+" successfully created!\n");
    }

    private void viewRoomTypeDetailsOperation() 
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Operation Manager Operations :: View Room Type Details ***\n");
        System.out.print("Enter Name Of Room Type> ");
        try{
            RoomTypeEntity roomType=roomTypeEntityController.retrieveRoomTypeByName(sc.nextLine().trim());
            printRoomTypeDetails(roomType);
        }catch(RoomTypeNotFoundException ex){
            System.out.println(ex.getMessage()+"\n");
        }
    }
    
    private void printRoomTypeDetails(RoomTypeEntity roomType)
    {
        System.out.println("Name: "+roomType.getName());
        System.out.println("Description: "+roomType.getDescription());
        System.out.println("Amenities: "+roomType.getAmenities());
        System.out.println("Bed Type: "+roomType.getBedTypeEnum().toString());
        System.out.println("Capacity: "+roomType.getCapacity().toString());
        System.out.println("Size: "+roomType.getSize().toString());
        System.out.println("Disabled: "+roomType.getDisable().toString());
        System.out.println("Used: "+roomType.getUsed()+"\n");
    }
    
    private void printRoomDetails(RoomEntity room) 
    {
        System.out.println("Room Number: "+room.getRoomNumber());
        System.out.println("Room Type: "+room.getRoomTypeEntity().getName());
    }   

    private void updateRoomTypeOperations() 
    {
        Scanner sc = new Scanner(System.in);
        String input;
        System.out.println("*** Operation Manager Operations :: Update Room Type ***\n");
        try{
            System.out.print("Enter Name Of Room Type> ");
            RoomTypeEntity roomType=roomTypeEntityController.retrieveRoomTypeByName(sc.nextLine().trim());
            System.out.println("Current Details:");
            printRoomTypeDetails(roomType);
            System.out.print("Enter New Name Of Room Type (blank if no change)> ");
            input=sc.nextLine().trim();
            if(input.length()>0)
            {
                roomType.setName(input);
            }
            System.out.print("Enter New Description (blank if no change)> ");
            input=sc.nextLine().trim();
            if(input.length()>0)
            {
                roomType.setDescription(input);
            }
            System.out.print("Enter New Size Of Room Type (blank if no change)> ");
            input=sc.nextLine().trim();
            if(input.length()>0)
            {
                roomType.setSize(Integer.parseInt(input));
            }
            System.out.print("Enter New Type Of Bed\n1: Single\n2: Queen\n3: King\n4: Jumbo\n (blank if no change)\n>");
            input=sc.nextLine().trim();
            if(input.length()>0)
            {
                roomType.setBedTypeEnum(BedTypeEnum.values()[Integer.parseInt(input)-1]);
            }
       
            System.out.print("Enter New Capacity Of Room Type (blank if no change)> ");
            input=sc.nextLine().trim();
            if(input.length()>0)
            {
                roomType.setCapacity(Integer.parseInt(input));
            }
            System.out.print("Enter New Amenities Of Room Type with each amenity separated by a comma (blank if no change)> ");
            input=sc.nextLine().trim();
            if(input.length()>0)
            {
                roomType.setAmenities(input);
            }
            roomTypeEntityController.updateRoomType(roomType);
            System.out.println("Room Type updated successfully!\n");   
        }catch(RoomTypeNotFoundException ex){
            System.out.println(ex.getMessage()+"\n");
        }
    }

    private void deleteRoomTypeOperation() {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Operation Manager Operations :: Delete Room Type ***\n");
        System.out.print("Enter Name Of Room Type> ");
        String input;
        try{
            RoomTypeEntity roomType=roomTypeEntityController.retrieveRoomTypeByName(sc.nextLine().trim());
            printRoomTypeDetails(roomType);
            System.out.print("Are you sure you want to delete? Enter y to confirm>");
            input=sc.nextLine().trim();
            if(input.equals("y"))
            {
                try {
                    roomTypeEntityController.deleteRoomType(roomType);
                    System.out.println("Successfully Deleted Room Type!\n");
                } catch (RoomTypeStillUsedException ex) {
                    System.out.println(ex.getMessage()+"\n");
                } catch (RoomTypeAlreadyDisabledException ex) {
                    System.out.println(ex.getMessage()+"\n");
                }    
            }
            else
            {
                System.out.println("Room Type "+roomType.getName()+" is not deleted\n");
            }  
        }catch(RoomTypeNotFoundException ex){
            System.out.println(ex.getMessage()+"\n");
        }
    }

    private void viewAllRoomTypesOperation() {
        System.out.println("*** Operation Manager Operations :: View All Room Types ***\n");
        List<RoomTypeEntity> roomTypes = roomTypeEntityController.retrieveAllRoomTypes();
        for(RoomTypeEntity roomType:roomTypes)
        {
            System.out.println();
            printRoomTypeDetails(roomType);
        }
        System.out.println();
        
    }

    private void createNewRoomOperation() {
        Scanner sc = new Scanner(System.in);
        RoomEntity room=new RoomEntity();
        String input;
        System.out.println("*** Operation Manager Operations :: Create New Room ***\n");
        System.out.print("Enter Room Number> ");
        input="";
        while(input.length()!=4)
        {
            input=sc.nextLine().trim();
            if(input.length()==4)
            {
                room.setRoomNumber(input);
                break;
            }
            System.out.print("Room Number invalid, input again> ");
            input=sc.nextLine().trim();           
        }
        System.out.println("Enter Name Of Room Type For Room> ");
        try{
            String roomTypeName = sc.nextLine().trim();
            roomEntityController.createNewRoom(room, roomTypeName);
        }catch(RoomTypeNotFoundException ex){
            System.out.println("Error in creating new Room: "+ex.getMessage()+"\n"); 
        }
    }

    private void updateRoomOperation() {
        Scanner sc = new Scanner(System.in);
        String input;
        System.out.println("*** Operation Manager Operations :: Update Room ***\n");
       
        System.out.print("Enter Room Number To Update> ");
        try{
            RoomEntity room=roomEntityController.retrieveRoomByNumber(sc.nextLine().trim());
            System.out.println("Current Details:");
            printRoomDetails(room);
            System.out.print("Enter New Room Number (blank if no change)> ");
            input=" ";
            while(input.length()>0&&input.length()!=4)
            {
                input=sc.nextLine().trim();
                if(input.length()==0){
                    break;
                }else if(input.length()==4){
                    room.setRoomNumber(input);
                    break;
                }
                System.out.print("Room Number invalid, input again (blank if no change)> "); 
            }
            System.out.print("Enter New Room Type of Room (blank if no change)> ");
            List<RoomTypeEntity> roomTypeList = roomTypeEntityController.retrieveAllRoomTypes();
            int numRoomType = roomTypeList.size();
            int i;
            Integer response = 0;
            while(true){
                for (i = 1; i <= numRoomType; i++){
                    System.out.println("" + i + ": " + roomTypeList.get(i-1).getName());
                }
                int lastOption = i+1;
                System.out.println("" + lastOption + ": No Change\n");
                System.out.print("> ");
                response = sc.nextInt();
                sc.nextLine().trim();
                
                while (response < 1 || response > lastOption){
                    System.out.println("Invalid response! Please try again.");
                    System.out.print("> ");
                    response = sc.nextInt();
                    sc.nextLine();
                }

                if (response == lastOption){
                    break;
                }

                else{
                    room.setRoomTypeEntity(roomTypeList.get(i-1));
                    break;
                }
            }
        }catch(RoomNotFoundException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void deleteRoomOperation()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Operation Manager Operations :: Delete Room ***\n");
        System.out.print("Enter Room Number> ");
        String input;
        try{
            RoomEntity room=roomEntityController.retrieveRoomByNumber(sc.nextLine().trim());
            printRoomDetails(room);
            System.out.print("Are you sure you want to delete? Enter y to confirm>");
            input=sc.nextLine().trim();
            if(input.equals("y"))
            {
                try {
                    roomEntityController.deleteRoom(room);
                    System.out.println("Successfully Deleted Room!\n");
                } catch (RoomIsUsedException ex) {
                    System.out.println(ex.getMessage()+"\n");
                } catch (RoomAlreadyDisabledException ex) {
                    System.out.println(ex.getMessage()+"\n");
                }
            }
            else
            {
                System.out.println("Room Type is not deleted\n");
            }
            
        }catch(RoomNotFoundException ex){
            System.out.println(ex.getMessage()+"\n");
        }
    }

    private void viewAllRoomsOperation() 
    {
        System.out.println("*** Operation Manager Operations :: View All Rooms ***\n");
        List<RoomEntity> rooms = roomEntityController.retrieveAllRooms();
        for(RoomEntity room:rooms)
        {
            System.out.println();
            printRoomDetails(room);
        }
        System.out.println();
    }

    private void viewRoomAllocationExceptionResultOperation() {
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*** Operation Manager Operations :: Room Allocation Exception Report ***\n");
        System.out.println("Type date of report to retrieve ((dd/MM/yyyy)");
        System.out.print("> ");
        String response = scanner.nextLine();
        Date date = new Date();
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(response); 
            } catch (Exception e){
                System.out.println(e);
            }
        while(!response.matches("\\d{2}/\\d{2}/\\d{4}")) {
            System.out.println("Invalid response! Please try again.");
            System.out.print("> ");
            response = scanner.nextLine();
            try {
                date = new SimpleDateFormat("dd/MM/yyyy").parse(response); 
            } catch (Exception e){
                System.out.println(e);
            }              
        }
        
        RoomExceptionReportEntity report = reportEntityController.retrieveReportByDate(date);
        
        System.out.println("First Exceptions:");
        List<AllocationExceptionEntity> firstExceptions = report.getFirstExceptionList();
        System.out.printf("%10s%20s%20s", "Rev ID", "Reserved Room", "Allocated Room");
        for(AllocationExceptionEntity exception:firstExceptions){
            System.out.printf("%10s%20s%20s", exception.getReservationEntity().getReservationId(), exception.getReservedRoomType().getName(), exception.getAllocatedRoomType().getName());
        }
        
        System.out.println("Second Exceptions:");
        List<AllocationExceptionEntity> secondExceptions = report.getSecondExceptionList();
        System.out.printf("%10s%20s", "Rev ID", "Reserved Room");
        for(AllocationExceptionEntity exception:secondExceptions){
            System.out.printf("%10s%20s", exception.getReservationEntity().getReservationId(), exception.getReservedRoomType().getName());
        }
    }
}
