/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.EmployeeEntityControllerLocal;
import ejb.session.stateless.RoomEntityControllerLocal;
import ejb.session.stateless.RoomTypeEntityControllerLocal;
import entity.EmployeeEntity;
import entity.RoomEntity;
import entity.RoomTypeEntity;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import util.enumeration.BedTypeEnum;
import util.enumeration.EmployeeAccessRightsEnum;
import util.exception.EmployeeNotFoundException;

/**
 *
 * @author Lance
 */
@Singleton
@LocalBean
@Startup
public class InitSessionBean 
{

    @EJB
    private RoomEntityControllerLocal roomEntityController;

    @EJB
    private RoomTypeEntityControllerLocal roomTypeEntityController;

    @EJB
    private EmployeeEntityControllerLocal employeeEntityController;
    
    public InitSessionBean() {
    }
    @PostConstruct
    public void postConstruct()
    {
        try
        {
            employeeEntityController.retrieveEmployeeByUsername("Admin");
        }
        catch(EmployeeNotFoundException ex)
        {
            initializeData();
        }
    }

    private void initializeData() 
    {
        employeeEntityController.createNewEmployee(new EmployeeEntity(null, "Admin", "Admin", "Admin","password",EmployeeAccessRightsEnum.SYSTEM_ADMINISTRATOR));
     
        RoomTypeEntity roomType1 = new RoomTypeEntity();
        roomType1.setName("Deluxe");
        roomType1.setAmenities("");
        roomType1.setBedTypeEnum(BedTypeEnum.QUEEN);
        roomType1.setCapacity(2);
        roomType1.setDescription("");
        roomType1.setSize(25);
        roomType1=roomTypeEntityController.createNewRoomType(roomType1);
        RoomTypeEntity roomType2 = new RoomTypeEntity();
        roomType2.setName("Premier");
        roomType2.setAmenities("");
        roomType2.setBedTypeEnum(BedTypeEnum.KING);
        roomType2.setCapacity(3);
        roomType2.setDescription("");
        roomType2.setSize(40);
        roomType2=roomTypeEntityController.createNewRoomType(roomType2);
        RoomTypeEntity roomType3 = new RoomTypeEntity();
        roomType3.setName("Family");
        roomType3.setAmenities("");
        roomType3.setBedTypeEnum(BedTypeEnum.KING);
        roomType3.setCapacity(5);
        roomType3.setDescription("");
        roomType3.setSize(50);
        roomType3=roomTypeEntityController.createNewRoomType(roomType3);
        RoomTypeEntity roomType4 = new RoomTypeEntity();
        roomType4.setName("Junior Suite");
        roomType4.setAmenities("");
        roomType4.setBedTypeEnum(BedTypeEnum.JUMBO);
        roomType4.setCapacity(7);
        roomType4.setDescription("");
        roomType4.setSize(70);
        roomType4=roomTypeEntityController.createNewRoomType(roomType4);
        RoomTypeEntity roomType5 = new RoomTypeEntity();
        roomType5.setName("Grand Suite");
        roomType5.setAmenities("");
        roomType5.setBedTypeEnum(BedTypeEnum.JUMBO);
        roomType5.setCapacity(8);
        roomType5.setDescription("");
        roomType5.setSize(100);
        roomType5=roomTypeEntityController.createNewRoomType(roomType5);
        
        //populate the rooms
        for(int i=1;i<=10;i++)
        {
            for(int j=1;j<=50;j++)
            {
                String roomNum;
                RoomEntity room;
                if(i<10&&j<10)
                {
                    roomNum=""+0+i+0+j;
                }
                else if(i<10)
                {
                    roomNum=""+0+i+j;
                }
                else if(i==10 && j<10)
                {
                    roomNum=""+i+0+j;
                }
                else
                {
                    roomNum=""+i+j;
                }
                
                if(i<=2)
                {
                    room= new RoomEntity();
                    room.setRoomNumber(roomNum);
                    room.setRoomTypeEntity(roomType1);
                    room=roomEntityController.createNewRoom(room);
                    roomType1.getRoomEntities().add(room);
                }
                else if(i<=4)
                {
                    room= new RoomEntity();
                    room.setRoomNumber(roomNum);
                    room.setRoomTypeEntity(roomType2);
                    room=roomEntityController.createNewRoom(room);
                    roomType2.getRoomEntities().add(room);
                }
                else if(i<=7)
                {
                    room= new RoomEntity();
                    room.setRoomNumber(roomNum);
                    room.setRoomTypeEntity(roomType3);
                    room=roomEntityController.createNewRoom(room);
                    roomType3.getRoomEntities().add(room);
                }
                else if(i<=9)
                {
                    room= new RoomEntity();
                    room.setRoomNumber(roomNum);
                    room.setRoomTypeEntity(roomType4);
                    room=roomEntityController.createNewRoom(room);
                    roomType4.getRoomEntities().add(room);
                }
                else if(i==10)
                {
                    room= new RoomEntity();
                    room.setRoomNumber(roomNum);
                    room.setRoomTypeEntity(roomType5);
                    room=roomEntityController.createNewRoom(room);
                    roomType5.getRoomEntities().add(room);
                }
            }
        }
        
    }
}
