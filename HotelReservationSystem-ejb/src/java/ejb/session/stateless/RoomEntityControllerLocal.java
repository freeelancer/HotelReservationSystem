/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomEntity;
import util.exception.RoomAlreadyDisabledException;
import util.exception.RoomIsUsedException;
import util.exception.RoomNotFoundException;
import util.exception.RoomTypeNotFoundException;

/**
 *
 * @author Lance
 */
public interface RoomEntityControllerLocal {

    public void createNewRoom(RoomEntity room, String roomTypeName) throws RoomTypeNotFoundException;

    public RoomEntity retrieveRoomByNumber(String roomNumber) throws RoomNotFoundException;

    public void updateRoom(RoomEntity room);    
    
    public void deleteRoom(RoomEntity room) throws RoomIsUsedException, RoomAlreadyDisabledException;
    
    public RoomEntity retrieveRoomById(Long roomId) throws RoomNotFoundException;
}
