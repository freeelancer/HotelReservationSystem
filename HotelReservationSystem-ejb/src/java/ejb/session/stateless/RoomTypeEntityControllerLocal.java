/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomTypeEntity;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Lance
 */
public interface RoomTypeEntityControllerLocal 
{
    public RoomTypeEntity createNewRoomType(RoomTypeEntity newRoomTypeEntity);       
}
