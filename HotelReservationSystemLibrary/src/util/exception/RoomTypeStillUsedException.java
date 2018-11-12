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
public class RoomTypeStillUsedException extends Exception {

    /**
     * Creates a new instance of <code>RoomTypeStillUsedException</code> without
     * detail message.
     */
    public RoomTypeStillUsedException() {
    }

    /**
     * Constructs an instance of <code>RoomTypeStillUsedException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public RoomTypeStillUsedException(String msg) {
        super(msg);
    }
}
