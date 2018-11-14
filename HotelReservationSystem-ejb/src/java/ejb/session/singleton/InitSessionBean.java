/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.EmployeeEntityControllerLocal;
import ejb.session.stateless.RoomEntityControllerLocal;
import ejb.session.stateless.RoomRateEntityControllerLocal;
import ejb.session.stateless.RoomTypeEntityControllerLocal;
import entity.EmployeeEntity;
import entity.RoomEntity;
import entity.RoomRateEntity;
import entity.RoomTypeEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import util.enumeration.BedTypeEnum;
import util.enumeration.EmployeeAccessRightsEnum;
import util.enumeration.RateTypeEnum;
import util.exception.EmployeeNotFoundException;
import util.exception.RoomTypeNotFoundException;

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
    
    @EJB
    private RoomRateEntityControllerLocal roomRateEntityController;
    
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
        employeeEntityController.createNewEmployee(new EmployeeEntity(null, "System", "Admin", "Admin","password",EmployeeAccessRightsEnum.SYSTEM_ADMINISTRATOR));
        employeeEntityController.createNewEmployee(new EmployeeEntity(null, "Operation", "Manager", "Operation","password",EmployeeAccessRightsEnum.OPERATION_MANAGER));
        employeeEntityController.createNewEmployee(new EmployeeEntity(null, "Sales", "Manager", "Sales","password",EmployeeAccessRightsEnum.SALES_MANAGER));
        employeeEntityController.createNewEmployee(new EmployeeEntity(null, "Guest", "Relations Officer", "Guest","password",EmployeeAccessRightsEnum.GUEST_RELATION_OFFICER));

//     initialize roomtypes
        RoomTypeEntity roomType1 = new RoomTypeEntity();
        roomType1.setName("Deluxe");
        roomType1.setAmenities("");
        roomType1.setBedTypeEnum(BedTypeEnum.QUEEN);
        roomType1.setCapacity(2);
        roomType1.setDescription("");
        roomType1.setSize(25);
        roomType1=roomTypeEntityController.createNewRoomType(roomType1);
        
        RoomRateEntity roomRate1 = new RoomRateEntity();
        roomRate1.setName("Deluxe Normal");
        roomRate1.setRatePerNight(new BigDecimal(100.00));
        roomRate1.setRateTypeEnum(RateTypeEnum.NORMAL);
        roomRate1.setRoomType(roomType1);
        roomRate1=roomRateEntityController.createNewRoomRate(roomRate1);
        
        RoomRateEntity roomRate1a = new RoomRateEntity();
        roomRate1a.setName("Deluxe Peak");
        roomRate1a.setRatePerNight(new BigDecimal(150.00));
        roomRate1a.setRateTypeEnum(RateTypeEnum.PUBLISHED);
        roomRate1a.setRoomType(roomType1);
        roomRate1a=roomRateEntityController.createNewRoomRate(roomRate1a);
        
        List<RoomRateEntity> roomRates1 = new ArrayList<RoomRateEntity>();
        roomRates1.add(roomRate1);
        roomRates1.add(roomRate1a);
        roomType1.setRoomRateEntities(roomRates1);
        roomTypeEntityController.updateRoomType(roomType1);
        
        RoomTypeEntity roomType2 = new RoomTypeEntity();
        roomType2.setName("Premier");
        roomType2.setAmenities("");
        roomType2.setBedTypeEnum(BedTypeEnum.KING);
        roomType2.setCapacity(3);
        roomType2.setDescription("");
        roomType2.setSize(40);
        roomType2=roomTypeEntityController.createNewRoomType(roomType2);
        
        RoomRateEntity roomRate2 = new RoomRateEntity();
        roomRate2.setName("Premier Normal");
        roomRate2.setRatePerNight(new BigDecimal(200.00));
        roomRate2.setRateTypeEnum(RateTypeEnum.NORMAL);
        roomRate2.setRoomType(roomType2);
        roomRate2=roomRateEntityController.createNewRoomRate(roomRate2);
        
        RoomRateEntity roomRate2a = new RoomRateEntity();
        roomRate2a.setName("Premier Peak");
        roomRate2a.setRatePerNight(new BigDecimal(250.00));
        roomRate2a.setRateTypeEnum(RateTypeEnum.PUBLISHED);
        roomRate2a.setRoomType(roomType2);
        roomRate2a=roomRateEntityController.createNewRoomRate(roomRate2a);
        
        List<RoomRateEntity> roomRates2 = new ArrayList<RoomRateEntity>();
        roomRates2.add(roomRate2);
        roomRates2.add(roomRate2a);
        roomType2.setRoomRateEntities(roomRates2);
        roomTypeEntityController.updateRoomType(roomType2);
        
        RoomTypeEntity roomType3 = new RoomTypeEntity();
        roomType3.setName("Family");
        roomType3.setAmenities("");
        roomType3.setBedTypeEnum(BedTypeEnum.KING);
        roomType3.setCapacity(5);
        roomType3.setDescription("");
        roomType3.setSize(50);
        roomType3=roomTypeEntityController.createNewRoomType(roomType3);
        
        RoomRateEntity roomRate3 = new RoomRateEntity();
        roomRate3.setName("Family Normal");
        roomRate3.setRatePerNight(new BigDecimal(300.00));
        roomRate3.setRateTypeEnum(RateTypeEnum.NORMAL);
        roomRate3.setRoomType(roomType3);
        roomRate3=roomRateEntityController.createNewRoomRate(roomRate3);  
        
        RoomRateEntity roomRate3a = new RoomRateEntity();
        roomRate3a.setName("Family Peak");
        roomRate3a.setRatePerNight(new BigDecimal(350.00));
        roomRate3a.setRateTypeEnum(RateTypeEnum.PUBLISHED);
        roomRate3a.setRoomType(roomType3);
        roomRate3a=roomRateEntityController.createNewRoomRate(roomRate3a);
        
        List<RoomRateEntity> roomRates3 = new ArrayList<RoomRateEntity>();
        roomRates3.add(roomRate3);
        roomRates3.add(roomRate3a);
        roomType3.setRoomRateEntities(roomRates3);
        roomTypeEntityController.updateRoomType(roomType3);
        
        RoomTypeEntity roomType4 = new RoomTypeEntity();
        roomType4.setName("Junior Suite");
        roomType4.setAmenities("");
        roomType4.setBedTypeEnum(BedTypeEnum.JUMBO);
        roomType4.setCapacity(7);
        roomType4.setDescription("");
        roomType4.setSize(70);
        roomType4=roomTypeEntityController.createNewRoomType(roomType4);
        
        RoomRateEntity roomRate4 = new RoomRateEntity();
        roomRate4.setName("Junior Suite Normal");
        roomRate4.setRatePerNight(new BigDecimal(400.00));
        roomRate4.setRateTypeEnum(RateTypeEnum.NORMAL);
        roomRate4.setRoomType(roomType4);
        roomRate4=roomRateEntityController.createNewRoomRate(roomRate4);   
        
        RoomRateEntity roomRate4a = new RoomRateEntity();
        roomRate4a.setName("Junior Suite Peak");
        roomRate4a.setRatePerNight(new BigDecimal(450.00));
        roomRate4a.setRateTypeEnum(RateTypeEnum.PUBLISHED);
        roomRate4a.setRoomType(roomType4);
        roomRate4a=roomRateEntityController.createNewRoomRate(roomRate4a);
        
        List<RoomRateEntity> roomRates4 = new ArrayList<RoomRateEntity>();
        roomRates4.add(roomRate4);
        roomRates4.add(roomRate4a);
        roomType4.setRoomRateEntities(roomRates4);
        roomTypeEntityController.updateRoomType(roomType4);
        
        RoomTypeEntity roomType5 = new RoomTypeEntity();
        roomType5.setName("Grand Suite");
        roomType5.setAmenities("");
        roomType5.setBedTypeEnum(BedTypeEnum.JUMBO);
        roomType5.setCapacity(8);
        roomType5.setDescription("");
        roomType5.setSize(100);
        roomType5=roomTypeEntityController.createNewRoomType(roomType5);
        
        RoomRateEntity roomRate5 = new RoomRateEntity();
        roomRate5.setName("Grand Suite Normal");
        roomRate5.setRatePerNight(new BigDecimal(500.00));
        roomRate5.setRateTypeEnum(RateTypeEnum.NORMAL);
        roomRate5.setRoomType(roomType5);
        roomRate5=roomRateEntityController.createNewRoomRate(roomRate5);
        
        RoomRateEntity roomRate5a = new RoomRateEntity();
        roomRate5a.setName("Grand Suite Peak");
        roomRate5a.setRatePerNight(new BigDecimal(550.00));
        roomRate5a.setRateTypeEnum(RateTypeEnum.PUBLISHED);
        roomRate5a.setRoomType(roomType5);
        roomRate5a=roomRateEntityController.createNewRoomRate(roomRate5a);
        
        List<RoomRateEntity> roomRates5 = new ArrayList<RoomRateEntity>();
        roomRates5.add(roomRate5);
        roomRates5.add(roomRate5a);
        roomType5.setRoomRateEntities(roomRates5);
        roomTypeEntityController.updateRoomType(roomType5);
        
//        intialize all the rooms
        for(int i=1;i<=5;i++)
        {
            for(int j=1;j<=5;j++)
            {
                String roomNum=""+0+i+0+j;
                RoomEntity room;
                if(i==1)
                {
                    try {
                        room= new RoomEntity();
                        room.setRoomNumber(roomNum);   
                        roomEntityController.createNewRoom(room,roomType1.getName());
                    } catch (RoomTypeNotFoundException ex) {
                    }
                }
                else if(i==2)
                {
                    try {
                        room= new RoomEntity();
                        room.setRoomNumber(roomNum);   
                        roomEntityController.createNewRoom(room,roomType2.getName());
                    } catch (RoomTypeNotFoundException ex) {
                    }
                }
                else if(i==3)
                {
                    try {
                        room= new RoomEntity();
                        room.setRoomNumber(roomNum);   
                        roomEntityController.createNewRoom(room,roomType3.getName());
                    } catch (RoomTypeNotFoundException ex) {
                    }
                }
                else if(i==4)
                {
                    try {
                        room= new RoomEntity();
                        room.setRoomNumber(roomNum);   
                        roomEntityController.createNewRoom(room,roomType4.getName());
                    } catch (RoomTypeNotFoundException ex) {
                    }
                }
                else if(i==5)
                {
                    try {
                        room= new RoomEntity();
                        room.setRoomNumber(roomNum);   
                        roomEntityController.createNewRoom(room,roomType5.getName());
                    } catch (RoomTypeNotFoundException ex) {
                    }
                }
            }
        }  
    }
}