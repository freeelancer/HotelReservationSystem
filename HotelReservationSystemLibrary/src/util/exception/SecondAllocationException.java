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
public class SecondAllocationException extends Exception {

    /**
     * Creates a new instance of <code>SecondAllocationException</code> without
     * detail message.
     */
    public SecondAllocationException() {
    }

    /**
     * Constructs an instance of <code>SecondAllocationException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public SecondAllocationException(String msg) {
        super(msg);
    }
}
