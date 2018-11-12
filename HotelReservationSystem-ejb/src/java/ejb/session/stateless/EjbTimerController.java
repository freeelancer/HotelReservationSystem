/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author Wai Kin
 */
@Stateless
@Local(EjbTimerControllerLocal.class)
@Remote(EjbTimerControllerRemote.class)
public class EjbTimerController implements EjbTimerControllerRemote, EjbTimerControllerLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
