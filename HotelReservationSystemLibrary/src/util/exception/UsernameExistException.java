/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author Wai Kin
 */
public class UsernameExistException extends Exception {

    /**
     * Creates a new instance of <code>AccountExistException</code> without
     * detail message.
     */
    public UsernameExistException() {
    }

    /**
     * Constructs an instance of <code>AccountExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UsernameExistException(String msg) {
        super(msg);
    }
}
