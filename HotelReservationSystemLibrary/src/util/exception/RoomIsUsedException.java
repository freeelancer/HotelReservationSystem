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
public class RoomIsUsedException extends Exception {

    /**
     * Creates a new instance of <code>RoomIsUsedException</code> without detail
     * message.
     */
    public RoomIsUsedException() {
    }

    /**
     * Constructs an instance of <code>RoomIsUsedException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public RoomIsUsedException(String msg) {
        super(msg);
    }
}
