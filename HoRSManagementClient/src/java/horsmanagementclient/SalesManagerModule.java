/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horsmanagementclient;

import ejb.session.stateless.RoomRateEntityControllerRemote;
import ejb.session.stateless.RoomTypeEntityControllerRemote;
import entity.EmployeeEntity;
import entity.RoomRateEntity;
import entity.RoomTypeEntity;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import util.enumeration.RateTypeEnum;
import util.exception.RoomRateAlreadyDisabledException;
import util.exception.RoomRateIsUsedException;
import util.exception.RoomRateNotFoundException;
import util.exception.RoomTypeNotFoundException;

/**
 *
 * @author Lance
 */
class SalesManagerModule {
    private EmployeeEntity currentEmployeeEntity;
    private RoomRateEntityControllerRemote roomRateEntityController;
    private RoomTypeEntityControllerRemote roomTypeEntityController;

    public SalesManagerModule() 
    {
    }

    public SalesManagerModule(EmployeeEntity currentEmployeeEntity, RoomRateEntityControllerRemote roomRateEntityController,RoomTypeEntityControllerRemote roomTypeEntityController) 
    {
        this.currentEmployeeEntity = currentEmployeeEntity;
        this.roomRateEntityController = roomRateEntityController;
        this.roomTypeEntityController = roomTypeEntityController;
    }

    void salesManagerOperations() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("1: Create New Room Rate");
            System.out.println("2: View Room Rate Details");
            System.out.println("3: Update Room Rate");
            System.out.println("4: Delete Room Rate");
            System.out.println("5: View All Room Rates");
            System.out.println("6: Logout\n");
            response = 0;
            
            while(response < 1 || response > 6)
            {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1)
                {
                    createNewRoomRateOperation();
                }
                else if(response == 2)
                {
                    viewRoomRateDetailsOperation();
                }
                else if (response == 3)
                {
                    updateRoomRateOperations();
                }
                else if (response == 4)
                {
                    deleteRoomRateOperation();
                }
                else if (response == 5)
                {
                    viewAllRoomRatesOperation();
                }
                else if (response == 6)
                {
                    break;
                }
                else
                {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 6)
            {
                break;
            }
        }    
    }

    private void createNewRoomRateOperation() 
    {
        Scanner sc = new Scanner(System.in);
        RoomRateEntity rate = new RoomRateEntity();
        System.out.println("*** Sales Manager Operations :: Create New Room Rate ***\n");
        System.out.print("Enter Room Rate Name> ");
        String roomRateName="";
        while(roomRateName.length()==0)
        {
            roomRateName=sc.nextLine().trim();
            if(roomRateName.length()>0)
            {
                break;
            }
            System.out.print("Rate Name not keyed, input again> ");
            roomRateName=sc.nextLine().trim();           
        }
        rate.setName(roomRateName);
        System.out.print("Enter Room Rate Per Night> ");
        rate.setRatePerNight(sc.nextBigDecimal());
        sc.nextLine();
        String response = "";
        Date validStart = new Date();
        Date validEnd = new Date();
        System.out.println("Type the dates (dd/MM/yyyy) for the validity period.");
        System.out.println("Valid From:");
        System.out.print("> ");
        response = sc.nextLine();
        try {
            validStart = new SimpleDateFormat("dd/MM/yyyy").parse(response); 
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        while(!response.matches("\\d{2}/\\d{2}/\\d{4}") || !validateCheckIn(validStart)) {

            System.out.println("Invalid response! Please try again.");

            System.out.print("> ");
            response = sc.nextLine();
            try {
                validStart = new SimpleDateFormat("dd/MM/yyyy").parse(response); 
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }              
        } 

        System.out.println("Valid Till:");

        System.out.print("> ");
        response = sc.nextLine();

        try {
            validEnd = new SimpleDateFormat("dd/MM/yyyy").parse(response); 
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }  

        while(!response.matches("\\d{2}/\\d{2}/\\d{4}") || !validateCheckOut(validStart, validEnd)) {

            System.out.println("Invalid response! Please try again.");

            System.out.print("> ");
            response = sc.nextLine();

            try {
                validEnd = new SimpleDateFormat("dd/MM/yyyy").parse(response); 
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }                
        }
        List<Date> dates = new ArrayList<Date>();
        for(Date current = validStart; current.before(validEnd); )
        {
            dates.add(current);
            
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(current);
            calendar.add(Calendar.DATE, 1);
            current = calendar.getTime();
            
        }
        dates.add(validEnd);
        rate.setValidityPeriod(dates);
        System.out.print("Enter Room Type Name For This Room Rate> ");
        String roomTypeName="";
        while(roomTypeName.length()==0)
        {
            roomTypeName=sc.nextLine().trim();
            if(roomTypeName.length()>0)
            {
                break;
            }
            System.out.print("Room Name not keyed, input again> ");
            roomTypeName=sc.nextLine().trim();           
        }
        System.out.print("Enter Type Of Rate\n1: Published\n2: Normal\n3: Peak\n4: Promotion\n>");
        int rateType=0;
        while(rateType<1||rateType>4)
        {
            rateType=sc.nextInt();
            sc.nextLine();
            if(rateType>0&&rateType<5)
            {
                break;
            }
            System.out.print("Invalid entry, input again> ");
        }
        rate.setRateTypeEnum(RateTypeEnum.values()[rateType-1]);
        try{
            roomRateEntityController.createNewRoomRate(rate, roomTypeName);
            System.out.println("Successfully Created Room Rate "+roomRateName+"!\n");
        }catch(RoomTypeNotFoundException ex){
            System.out.println("Error in creating new Rate Type: "+ex.getMessage()+"\n"); 
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

    private void viewRoomRateDetailsOperation() 
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Operation Manager Operations :: View Room Rate Details ***\n");
        System.out.print("Enter Room Rate Name> ");
        String rateName = sc.nextLine().trim();
        try
        {
            RoomRateEntity rate = roomRateEntityController.retrieveRoomRateByName(rateName);
            printRoomRateDetails(rate);
            System.out.println();
        }
        catch(RoomRateNotFoundException ex)
        {
            System.out.println(ex.getMessage());
        }
        
    }

    private void updateRoomRateOperations() 
    {
        Scanner sc = new Scanner(System.in);
        String input;
        System.out.println("*** Sales Manager Operations :: Update Room Rate ***\n");
        try{
            System.out.print("Enter Name Of Room Rate> ");
            RoomRateEntity roomRate=roomRateEntityController.retrieveRoomRateByName(sc.nextLine().trim());
            System.out.println("Current Details:");
            printRoomRateDetails(roomRate);
            System.out.print("Enter New Name Of Room Type (blank if no change)> ");
            input=sc.nextLine().trim();
            if(input.length()>0)
            {
                roomRate.setName(input);
            }
            System.out.print("Enter New Rate Per Night (0 if no change)> ");
            
            BigDecimal ratePerNight = sc.nextBigDecimal();
            sc.nextLine();
                   
            if(ratePerNight.compareTo(BigDecimal.ZERO)!=0)
            {
                roomRate.setRatePerNight(ratePerNight);
            }
            System.out.print("Enter the Associated Room Type (6 if no change)>\n");
            List<RoomTypeEntity> roomTypeList = roomTypeEntityController.retrieveAllRoomTypes();
            int numRoomType = roomTypeList.size();
            int i;
            Integer response = 0;
            RoomTypeEntity roomType = new RoomTypeEntity();
            while(true){
                for (i = 1; i <= numRoomType; i++){
                    System.out.println("" + i + ": " + roomTypeList.get(i-1).getName());
                }
                int lastOption = numRoomType+1;
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
                else
                {
                    roomType = roomTypeList.get(response-1);
                    break;
                }
            }
            System.out.print("Enter New Rate Type\n1: Published\n2: Normal\n3: Peak\n4: Promotion\n (Enter if no change)\n>");
            input=sc.nextLine().trim();
            if(input.length()>0)
            {
                roomRate.setRateTypeEnum(RateTypeEnum.values()[Integer.parseInt(input)-1]);
            }
            System.out.print("Enter 'y' if there is a change in the Validity Period (blank if no change)\n>");
            input = sc.nextLine().trim();
            if(input.equals("y")){
                Date validStart = new Date();
                Date validEnd = new Date();
                System.out.println("Type the dates (dd/MM/yyyy) for the validity period.");
                System.out.println("Valid From:");
                System.out.print("> ");
                input = sc.nextLine().trim();
                try {
                    validStart = new SimpleDateFormat("dd/MM/yyyy").parse(input); 
                } catch (Exception ex){
                    System.out.println(ex.getMessage());
                }

                while(!input.matches("\\d{2}/\\d{2}/\\d{4}")) {

                    System.out.println("Invalid response! Please try again.");

                    System.out.print("> ");
                    input = sc.nextLine().trim();
                    try {
                        validStart = new SimpleDateFormat("dd/MM/yyyy").parse(input); 
                    } catch (Exception ex){
                        System.out.println(ex.getMessage());
                    }              
                } 

                System.out.println("Valid Till:");

                System.out.print("> ");
                input = sc.nextLine();

                try {
                    validEnd = new SimpleDateFormat("dd/MM/yyyy").parse(input); 
                } catch (Exception ex){
                    System.out.println(ex.getMessage());
                }  

                while(!input.matches("\\d{2}/\\d{2}/\\d{4}") || !validateCheckOut(validStart, validEnd)) {

                    System.out.println("Invalid response! Please try again.");

                    System.out.print("> ");
                    input = sc.nextLine();

                    try {
                        validEnd = new SimpleDateFormat("dd/MM/yyyy").parse(input); 
                    } catch (Exception ex){
                        System.out.println(ex.getMessage());
                    }                
                }
                List<Date> dates = new ArrayList<Date>();
                for(Date current = validStart; current.before(validEnd); )
                {
                    dates.add(current);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(current);
                    calendar.add(Calendar.DATE, 1);
                    current = calendar.getTime();

                }
                dates.add(validEnd);
                roomRate.setValidityPeriod(dates);
            }
            roomRate = roomRateEntityController.updateRoomRate(roomRate,roomType.getName());
            System.out.println("Room Rate Updated Successfully!\n");
            printRoomRateDetails(roomRate);
            System.out.println();
        }catch(RoomRateNotFoundException ex){
            System.out.println(ex.getMessage()+"\n");
        }
    }

    private void deleteRoomRateOperation() 
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Operation Manager Operations :: Delete Room Rate ***\n");
        System.out.print("Enter Room Rate Name> ");
        String input;
        try{
            RoomRateEntity rate=roomRateEntityController.retrieveRoomRateByName(sc.nextLine().trim());
            printRoomRateDetails(rate);
            System.out.print("Are you sure you want to delete? Enter y to confirm>");
            input=sc.nextLine().trim();
            if(input.equals("y"))
            {
                try {
                    roomRateEntityController.deleteRoomRate(rate);
                    System.out.println("Successfully Deleted Room!\n");
                } catch (RoomRateIsUsedException ex) {
                    System.out.println(ex.getMessage()+"\n");
                } catch (RoomRateAlreadyDisabledException ex) {
                    System.out.println(ex.getMessage()+"\n");
                }
            }
            else
            {
                System.out.println("Room Type is not deleted\n");
            }
            
        }catch(RoomRateNotFoundException ex){
            System.out.println(ex.getMessage()+"\n");
        }
    }

    private void viewAllRoomRatesOperation() 
    {
        System.out.println("*** Operation Manager Operations :: View All Room Rates ***\n");
        List<RoomRateEntity> rates = roomRateEntityController.retrieveAllRoomRates();
        for(RoomRateEntity rate:rates)
        {
            System.out.println();
            printRoomRateDetails(rate);
        }
        System.out.println();    
    }

    private void printRoomRateDetails(RoomRateEntity rate) 
    {
        System.out.println("Room Rate Name: "+rate.getName());
        System.out.println("Room Type Of Rate: "+rate.getRoomType().getName());
        System.out.println("Room Rate Type: "+rate.getRateTypeEnum().toString());
        System.out.println("Room Rate Per Night: "+rate.getRatePerNight());
        if(rate.getValidityPeriod()!=null&&!rate.getValidityPeriod().isEmpty())
        {
            System.out.println("Valid From: "+rate.getValidityPeriod().get(0).toString());
            System.out.println("Valid To: "+rate.getValidityPeriod().get(rate.getValidityPeriod().size()-1).toString());
        }
        
    }
    
}
