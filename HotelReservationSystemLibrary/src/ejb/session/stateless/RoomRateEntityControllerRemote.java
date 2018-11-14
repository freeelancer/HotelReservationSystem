/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomRateEntity;
import java.util.List;
import util.exception.RoomRateAlreadyDisabledException;
import util.exception.RoomRateIsUsedException;
import util.exception.RoomRateNotFoundException;
import util.exception.RoomTypeNotFoundException;

/**
 *
 * @author Lance
 */
public interface RoomRateEntityControllerRemote {

    public RoomRateEntity createNewRoomRate(RoomRateEntity roomRate, String roomTypeName) throws RoomTypeNotFoundException;

    public List<RoomRateEntity> retrieveAllRoomRates();

    public void deleteRoomRate(RoomRateEntity rate) throws RoomRateIsUsedException, RoomRateAlreadyDisabledException;
  
    public RoomRateEntity updateRoomRate(RoomRateEntity roomRate);

    public RoomRateEntity retrieveRoomRateByName(String rateName) throws RoomRateNotFoundException;

}
