/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomRateEntity;
import java.util.List;
import util.exception.RoomRateNotFoundException;

/**
 *
 * @author Lance
 */
public interface RoomRateEntityControllerRemote {

    public RoomRateEntity createNewRoomRate(RoomRateEntity roomRate);

    public RoomRateEntity retrieveRoomRateById(Long roomRateId) throws RoomRateNotFoundException;

//    public List<RoomRateEntity> retrieveAllRoomRates();
//
//    public RoomRateEntity updateRoomRate(RoomRateEntity roomRate);

    public void deleteRoomRate(RoomRateEntity roomRate);
    

}
