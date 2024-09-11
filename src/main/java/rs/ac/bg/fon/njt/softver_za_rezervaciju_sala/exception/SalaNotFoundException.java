/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.exception;

/**
 *
 * @author Stefan
 */
public class SalaNotFoundException extends RuntimeException{

    public SalaNotFoundException(String message) {
        super(message);
    }

    public SalaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SalaNotFoundException(Throwable cause) {
        super(cause);
    }

    
    
    
}
