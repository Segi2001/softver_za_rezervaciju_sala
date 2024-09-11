/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.exception.response;

/**
 *
 * @author Stefan
 */
public class SalaErrorResponse {
    
    
    private int status;
    
    private String message;

    public SalaErrorResponse() {
    }

    public SalaErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SalaErrorResponse{" + "status=" + status + ", message=" + message + '}';
    }
    
    
    
    
}
