/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horsmanagementclient;

import ejb.session.stateless.RoomEntityControllerRemote;
import ejb.session.stateless.RoomTypeEntityControllerRemote;
import entity.EmployeeEntity;
import entity.RoomTypeEntity;
import java.util.Scanner;
import util.enumeration.BedTypeEnum;

/**
 *
 * @author Lance
 */
class OperationManagerModule {
    private EmployeeEntity currentEmployeeEntity;
    private RoomTypeEntityControllerRemote roomTypeEntityController;
    private RoomEntityControllerRemote roomEntityController;

    public OperationManagerModule() {
    }

    public OperationManagerModule(EmployeeEntity currentEmployeeEntity, RoomTypeEntityControllerRemote roomTypeEntityController, RoomEntityControllerRemote roomEntityController) 
    {
        this.currentEmployeeEntity = currentEmployeeEntity;
        this.roomTypeEntityController = roomTypeEntityController;
        this.roomEntityController = roomEntityController;
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
            System.out.println("11: Logout\n");
            response = 0;
            
            while(response < 1 || response > 11)
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
                    UpdateRoomTypeOperations();
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
                    deleteRoomOpertaion();
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
                    break;
                }
                else
                {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 11)
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
        newRoomTypeEntity.setSize(sc.nextLine().trim());
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
    }

    private void viewRoomTypeDetailsOperation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void UpdateRoomTypeOperations() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void deleteRoomTypeOperation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void viewAllRoomTypesOperation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void createNewRoomOperation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void updateRoomOperation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void deleteRoomOpertaion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void viewAllRoomsOperation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void viewRoomAllocationExceptionResultOperation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
