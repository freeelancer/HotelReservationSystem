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
public class RoomTypeAlreadyDisabledException extends Exception {

    /**
     * Creates a new instance of <code>RoomTypeAlreadyDisabledException</code>
     * without detail message.
     */
    public RoomTypeAlreadyDisabledException() {
    }

    /**
     * Constructs an instance of <code>RoomTypeAlreadyDisabledException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public RoomTypeAlreadyDisabledException(String msg) {
        super(msg);
    }
}
