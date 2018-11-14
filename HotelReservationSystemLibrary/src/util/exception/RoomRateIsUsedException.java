/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author Lance
 */
public class RoomRateIsUsedException extends Exception {

    /**
     * Creates a new instance of <code>RoomRateIsUsedException</code> without
     * detail message.
     */
    public RoomRateIsUsedException() {
    }

    /**
     * Constructs an instance of <code>RoomRateIsUsedException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public RoomRateIsUsedException(String msg) {
        super(msg);
    }
}
