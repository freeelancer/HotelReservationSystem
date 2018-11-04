/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomTypeEntity;
import util.exception.RoomTypeNotFoundException;

/**
 *
 * @author Lance
 */
public interface RoomTypeEntityControllerLocal 
{
    public RoomTypeEntity createNewRoomType(RoomTypeEntity newRoomTypeEntity);    
    
    public RoomTypeEntity retrieveRoomTypeByName(String roomTypeName) throws RoomTypeNotFoundException;
    
    public void updateRoomType(RoomTypeEntity roomType);
    
    public String deleteRoomType(RoomTypeEntity roomType);
}
