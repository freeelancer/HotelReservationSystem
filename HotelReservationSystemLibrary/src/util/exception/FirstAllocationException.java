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
public class FirstAllocationException extends Exception {

    /**
     * Creates a new instance of <code>FirstAllocationException</code> without
     * detail message.
     */
    public FirstAllocationException() {
    }

    /**
     * Constructs an instance of <code>FirstAllocationException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public FirstAllocationException(String msg) {
        super(msg);
    }
}
