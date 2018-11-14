/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomRateEntity;
import java.util.List;
import util.exception.DeleteRoomRateException;
import util.exception.RoomTypeNotFoundException;

/**
 *
 * @author Lance
 */
public interface RoomRateEntityControllerLocal {

    public RoomRateEntity createNewRoomRate(RoomRateEntity roomRate, String roomTypeName) throws RoomTypeNotFoundException;
    
    public RoomRateEntity createNewRoomRate(RoomRateEntity roomRate);

    public List<RoomRateEntity> retrieveAllRoomRates();

    public RoomRateEntity updateRoomRate(RoomRateEntity roomRate);

    public void deleteRoomRate(Long roomRateId) throws DeleteRoomRateException;

}
