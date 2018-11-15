/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.DateEntity;
import java.util.List;

/**
 *
 * @author Wai Kin
 */
public interface DateEntityControllerLocal {

    public DateEntity createNewDate(DateEntity date);
    
    public List<DateEntity> retrieveAllDateEntitysForRoomTypeId(Long roomTypeId);
    
}
