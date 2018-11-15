/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import entity.RoomTypeEntity;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Wai Kin
 */
@XmlRootElement
public class RetrieveAllRoomTypesResp {
    
    private List<RoomTypeEntity> roomTypeEntities;
    
    public RetrieveAllRoomTypesResp() {
    }

    public RetrieveAllRoomTypesResp(List<RoomTypeEntity> roomTypeEntities) {
        this.roomTypeEntities = roomTypeEntities;
    }

    public List<RoomTypeEntity> getRoomTypeEntities() {
        return roomTypeEntities;
    }

    public void setRoomTypeEntities(List<RoomTypeEntity> roomTypeEntities) {
        this.roomTypeEntities = roomTypeEntities;
    }
    
}
