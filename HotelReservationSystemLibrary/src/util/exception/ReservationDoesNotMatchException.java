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
public class ReservationDoesNotMatchException extends Exception {

    /**
     * Creates a new instance of <code>ReservationDoesNotMatchException</code>
     * without detail message.
     */
    public ReservationDoesNotMatchException() {
    }

    /**
     * Constructs an instance of <code>ReservationDoesNotMatchException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ReservationDoesNotMatchException(String msg) {
        super(msg);
    }
}
