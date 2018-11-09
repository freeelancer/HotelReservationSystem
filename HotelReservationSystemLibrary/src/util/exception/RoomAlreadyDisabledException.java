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
public class RoomAlreadyDisabledException extends Exception {

    /**
     * Creates a new instance of <code>RoomAlreadyDisabledException</code>
     * without detail message.
     */
    public RoomAlreadyDisabledException() {
    }

    /**
     * Constructs an instance of <code>RoomAlreadyDisabledException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public RoomAlreadyDisabledException(String msg) {
        super(msg);
    }
}
