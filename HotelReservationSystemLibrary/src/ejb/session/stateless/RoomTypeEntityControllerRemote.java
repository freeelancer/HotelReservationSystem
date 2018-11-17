/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomTypeEntity;
import java.util.Date;
import java.util.List;
import util.exception.RoomTypeAlreadyDisabledException;
import util.exception.RoomTypeNotFoundException;
import util.exception.RoomTypeStillUsedException;

/**
 *
 * @author Lance
 */
public interface RoomTypeEntityControllerRemote 
{
    public RoomTypeEntity createNewRoomType(RoomTypeEntity newRoomTypeEntity);    

    public RoomTypeEntity retrieveRoomTypeByName(String roomTypeName) throws RoomTypeNotFoundException;

    public void updateRoomType(RoomTypeEntity roomType);

    public void deleteRoomType(RoomTypeEntity roomType) throws RoomTypeStillUsedException, RoomTypeAlreadyDisabledException;

    public List<RoomTypeEntity> retrieveAllRoomTypes();

    public List<Date> checkAvailability(Date checkInDate, Date checkOutDate, RoomTypeEntity roomType, Integer numRooms);
}
